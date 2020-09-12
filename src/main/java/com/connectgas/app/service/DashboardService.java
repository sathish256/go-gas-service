package com.connectgas.app.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.connectgas.app.exception.ConnectGasDataAccessException;
import com.connectgas.app.model.Dealership;
import com.connectgas.app.model.common.ChartDataPoint;
import com.connectgas.app.model.common.Dashboard;
import com.connectgas.app.model.customer.Customer;
import com.connectgas.app.model.order.Order;
import com.connectgas.app.model.order.OrderStatus;
import com.connectgas.app.model.payment.AccountHolderType;
import com.connectgas.app.model.payment.PaymentBacklog;
import com.connectgas.app.model.user.User;
import com.connectgas.app.repository.SimpleFirestoreRepository;

@Service
public class DashboardService {

	@Autowired
	private SimpleFirestoreRepository<Order, String> orderRepository;

	@Autowired
	private SimpleFirestoreRepository<PaymentBacklog, String> paymentRepository;

	@Autowired
	private SimpleFirestoreRepository<Customer, String> customerRepository;

	@Autowired
	private SimpleFirestoreRepository<Dealership, String> dealerRepository;

	@Autowired
	private SimpleFirestoreRepository<User, String> userRepository;

	public Dashboard getDeliveryPersonDashboard(String modifiedBy) {

		LocalTime midnight = LocalTime.MIDNIGHT;
		LocalDate today = LocalDate.now(ZoneId.of("Asia/Kolkata"));
		final LocalDateTime fromDate = LocalDateTime.of(today, midnight);
		final LocalDateTime toDate = fromDate.plusDays(1);

		Predicate<Order> dailypredicate = o -> o.getOrderStatus().equals(OrderStatus.DELIVERED)
				&& o.getDeliveryPersonId().equals(modifiedBy) && LocalDateTime.parse(o.getCreatedAt()).isAfter(fromDate)
				&& LocalDateTime.parse(o.getCreatedAt()).isBefore(toDate);

		List<Order> todayOrders = orderRepository.findAll(Order.class).stream().filter(dailypredicate)
				.collect(Collectors.toList());

		final LocalDateTime before30Days = LocalDateTime.of(today.minusDays(30), midnight);

		Predicate<Order> monthlyPredicate = o -> o.getOrderStatus().equals(OrderStatus.DELIVERED)
				&& o.getDeliveryPersonId().equals(modifiedBy)
				&& LocalDateTime.parse(o.getCreatedAt()).isAfter(before30Days)
				&& LocalDateTime.parse(o.getCreatedAt()).isBefore(toDate);

		List<Order> last30DaysOrders = orderRepository.findAll(Order.class).stream().filter(monthlyPredicate)
				.collect(Collectors.toList());

		Dashboard dashboard = new Dashboard();
		dashboard.setTodayOrders(todayOrders.size());
		dashboard.setMonthlyOrders(last30DaysOrders.size());

		Double todayAmount = getAmountSummary(todayOrders);

		dashboard.setTodayAmount(todayAmount);

		Double monthlyAmount = getAmountSummary(last30DaysOrders);
		dashboard.setMonthlyAmount(monthlyAmount);

		List<ChartDataPoint> chartDataPoints = getChartData(last30DaysOrders);
		dashboard.setChartDataPoints(chartDataPoints);

		return dashboard;
	}

	public Dashboard getDealerDashboard(String dealer, String modifiedBy) {

		String modifiedByDealerId = userRepository.fetchById(modifiedBy, User.class).map(User::getDealershipId)
				.orElseThrow(() -> new ConnectGasDataAccessException("User Not found in the system"));

		final String dealerId = StringUtils.isEmpty(dealer) ? modifiedByDealerId : dealer;

		LocalTime midnight = LocalTime.MIDNIGHT;
		LocalDate today = LocalDate.now(ZoneId.of("Asia/Kolkata"));
		final LocalDateTime fromDate = LocalDateTime.of(today, midnight);
		final LocalDateTime toDate = fromDate.plusDays(1);

		Predicate<Order> dailypredicate = o -> o.getOrderStatus().equals(OrderStatus.DELIVERED)
				&& o.getDealerId().equals(dealerId) && LocalDateTime.parse(o.getCreatedAt()).isAfter(fromDate)
				&& LocalDateTime.parse(o.getCreatedAt()).isBefore(toDate);

		List<Order> todayOrders = orderRepository.findAll(Order.class).stream().filter(dailypredicate)
				.collect(Collectors.toList());

		final LocalDateTime before30Days = LocalDateTime.of(today.minusDays(30), midnight);

		Predicate<Order> monthlyPredicate = o -> o.getOrderStatus().equals(OrderStatus.DELIVERED)
				&& o.getDealerId().equals(dealerId) && LocalDateTime.parse(o.getCreatedAt()).isAfter(before30Days)
				&& LocalDateTime.parse(o.getCreatedAt()).isBefore(toDate);

		List<Order> last30DaysOrders = orderRepository.findAll(Order.class).stream().filter(monthlyPredicate)
				.collect(Collectors.toList());

		Dashboard dashboard = new Dashboard();
		dashboard.setTodayOrders(todayOrders.size());
		dashboard.setMonthlyOrders(last30DaysOrders.size());

		Double todayAmount = getAmountSummary(todayOrders);

		dashboard.setTodayAmount(todayAmount);

		Double monthlyAmount = getAmountSummary(last30DaysOrders);
		dashboard.setMonthlyAmount(monthlyAmount);

		Double totalDuesFromCustomers = getTotalDuesFromCustomers(dealerId);
		dashboard.setTotalDuesFromCustomers(totalDuesFromCustomers);

		List<ChartDataPoint> chartDataPoints = getChartData(last30DaysOrders);
		dashboard.setChartDataPoints(chartDataPoints);

		return dashboard;

	}

	private Double getTotalDuesFromCustomers(String dealerId) {

		final List<String> customerIdByDealer = customerRepository.findAll(Customer.class).stream()
				.filter(c -> c.getDealerId().equals(dealerId)).map(Customer::getId).collect(Collectors.toList());

		Predicate<PaymentBacklog> tdfcPredicate = p -> p.getAccountHolderType().equals(AccountHolderType.CUSTOMER)
				&& customerIdByDealer.contains(p.getId());
		List<PaymentBacklog> totalDuesFromCustomers = paymentRepository.findAll(PaymentBacklog.class).stream()
				.filter(tdfcPredicate).collect(Collectors.toList());

		return totalDuesFromCustomers.stream().mapToDouble(PaymentBacklog::getBacklogAmount).reduce(0, Double::sum);
	}

	public Dashboard getCandfDashboard(String userId) {

		String candfId = userRepository.fetchById(userId, User.class).map(User::getCandfId)
				.orElseThrow(() -> new ConnectGasDataAccessException("User Not found in the system"));

		final List<String> dealerIdbyCandf = dealerRepository.findAll(Dealership.class).stream()
				.filter(c -> c.getCandfId().equals(candfId)).map(Dealership::getId).collect(Collectors.toList());

		LocalTime midnight = LocalTime.MIDNIGHT;
		LocalDate today = LocalDate.now(ZoneId.of("Asia/Kolkata"));
		final LocalDateTime fromDate = LocalDateTime.of(today, midnight);
		final LocalDateTime toDate = fromDate.plusDays(1);

		Predicate<Order> dailypredicate = o -> o.getOrderStatus().equals(OrderStatus.DELIVERED)
				&& dealerIdbyCandf.contains(o.getDealerId()) && LocalDateTime.parse(o.getCreatedAt()).isAfter(fromDate)
				&& LocalDateTime.parse(o.getCreatedAt()).isBefore(toDate);

		List<Order> todayOrders = orderRepository.findAll(Order.class).stream().filter(dailypredicate)
				.collect(Collectors.toList());

		final LocalDateTime before30Days = LocalDateTime.of(today.minusDays(30), midnight);

		Predicate<Order> monthlyPredicate = o -> o.getOrderStatus().equals(OrderStatus.DELIVERED)
				&& dealerIdbyCandf.contains(o.getDealerId())
				&& LocalDateTime.parse(o.getCreatedAt()).isAfter(before30Days)
				&& LocalDateTime.parse(o.getCreatedAt()).isBefore(toDate);

		List<Order> last30DaysOrders = orderRepository.findAll(Order.class).stream().filter(monthlyPredicate)
				.collect(Collectors.toList());

		Dashboard dashboard = new Dashboard();
		dashboard.setTodayOrders(todayOrders.size());
		dashboard.setMonthlyOrders(last30DaysOrders.size());

		Double todayAmount = getAmountSummary(todayOrders);

		dashboard.setTodayAmount(todayAmount);

		Double monthlyAmount = getAmountSummary(last30DaysOrders);
		dashboard.setMonthlyAmount(monthlyAmount);

		Double totalDuesFromCustomers = 0.0;
		for (String dealerId : dealerIdbyCandf)
			totalDuesFromCustomers = totalDuesFromCustomers + getTotalDuesFromCustomers(dealerId);
		dashboard.setTotalDuesFromCustomers(totalDuesFromCustomers);

		Double totolDuesFromDealers = getTotalDuesFromDealers(candfId);
		dashboard.setTotolDuesFromDealers(totolDuesFromDealers);

		List<ChartDataPoint> chartDataPoints = getChartData(last30DaysOrders);
		dashboard.setChartDataPoints(chartDataPoints);
		return dashboard;
	}

	private double getAmountSummary(List<Order> todayOrders) {
		return todayOrders.stream().mapToDouble(o -> o.getPaymentInfo().getPaidDetails().stream()
				.mapToDouble(pd -> pd.getAmount()).reduce(0, Double::sum)).reduce(0, Double::sum);
	}

	private Double getTotalDuesFromDealers(String candfId) {

		final List<String> dealerIdbyCandF = dealerRepository.findAll(Dealership.class).stream()
				.filter(c -> c.getCandfId().equals(candfId)).map(Dealership::getId).collect(Collectors.toList());

		Predicate<PaymentBacklog> tdfcPredicate = p -> p.getAccountHolderType().equals(AccountHolderType.DEALER)
				&& dealerIdbyCandF.contains(p.getId());
		List<PaymentBacklog> totalDuesFromCustomers = paymentRepository.findAll(PaymentBacklog.class).stream()
				.filter(tdfcPredicate).collect(Collectors.toList());

		return totalDuesFromCustomers.stream().mapToDouble(PaymentBacklog::getBacklogAmount).reduce(0, Double::sum);
	}

	private List<ChartDataPoint> getChartData(List<Order> last30DaysOrders) {

		Map<String, Long> dataPointByDate = last30DaysOrders.stream()
				.filter(o -> !o.getOrderStatus().equals(OrderStatus.CANCELLED))

				.map(Order::getLastmodifiedAt).map(s -> LocalDateTime.parse(s))
				.map(d -> d.format(DateTimeFormatter.ISO_LOCAL_DATE))

				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		List<ChartDataPoint> dataPoints = new LinkedList<>();

		// Sort a map and add to finalMap
		dataPointByDate.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed())
				.forEachOrdered(e -> dataPoints.add(new ChartDataPoint(e.getKey(), e.getValue())));

		return dataPoints;
	}

}
