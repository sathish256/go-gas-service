package com.connectgas.app.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.connectgas.app.exception.ConnectGasDataAccessException;
import com.connectgas.app.model.common.ConnectGasEntity;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.hazelcast.core.HazelcastInstance;

@Repository
public class SimpleFirestoreRepository<E extends ConnectGasEntity, ID> implements FirestoreRepository<E, ID> {

	private final HazelcastInstance hazelcastInstance;

	@Autowired
	public SimpleFirestoreRepository(HazelcastInstance hazelcastInstance) {
		this.hazelcastInstance = hazelcastInstance;
	}

	@Override
	public E save(E entity, Class<E> persistentClass) {
		getFirestore().collection(persistentClass.getSimpleName()).document(entity.getId()).set(entity);
		Map<String, E> hazelcastMap = hazelcastInstance.getMap(persistentClass.getSimpleName());
		hazelcastMap.put(entity.getId(), entity);
		return entity;
	}

	@Override
	public Optional<E> fetchById(ID id, Class<E> persistentClass) throws ConnectGasDataAccessException {
		E entity = null;
		Map<String, E> hazelcastMap = hazelcastInstance.getMap(persistentClass.getSimpleName());
		hazelcastMap.get(id);

		if (entity == null) {
			DocumentReference docRef = getFirestore().collection(persistentClass.getSimpleName()).document((String) id);
			// asynchronously retrieve the document
			ApiFuture<DocumentSnapshot> future = docRef.get();
			// block on response
			DocumentSnapshot document;

			try {
				document = future.get();

				if (document.exists()) {
					entity = document.toObject(persistentClass);
				}
			} catch (InterruptedException | ExecutionException e) {
				throw new ConnectGasDataAccessException("Exception while fetch from firestore", e);
			}
		}
		return Optional.ofNullable(entity);
	}

	@Override
	public void deleteById(ID id, Class<E> persistentClass) throws ConnectGasDataAccessException {

		try {
			getFirestore().collection(persistentClass.getSimpleName()).document((String) id).delete();
		} catch (Exception e) {
			throw new ConnectGasDataAccessException("Exception while fetch from firestore", e);
		}

	}

	@Override
	public List<E> findAll(Class<E> persistentClass) {
		List<E> data = new ArrayList<>();

		Map<String, E> hazelcastMap = hazelcastInstance.getMap(persistentClass.getSimpleName());
		for (E e : hazelcastMap.values()) {
			data.add(e);
		}

		if (CollectionUtils.isEmpty(data)) {
			ApiFuture<QuerySnapshot> future = getFirestore().collection(persistentClass.getSimpleName()).get();
			try {
				for (DocumentSnapshot document : future.get().getDocuments()) {
					E e = document.toObject(persistentClass);
					data.add(e);
					hazelcastMap.put(e.getId(), e);
				}
			} catch (InterruptedException | ExecutionException e) {
				throw new ConnectGasDataAccessException("Exception while fetch from firestore", e);
			}
		}

		return data;
	}

	@Override
	@Deprecated
	public List<E> findByPathAndValue1(String path, String value, Class<E> persistentClass) {
		List<E> data = new ArrayList<>();
		CollectionReference cities = getFirestore().collection(persistentClass.getSimpleName());
		Query query = cities.whereEqualTo(path, value);
		ApiFuture<QuerySnapshot> querySnapshot = query.get();
		try {
			for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
				data.add(document.toObject(persistentClass));
			}
		} catch (InterruptedException | ExecutionException e) {
			throw new ConnectGasDataAccessException("Exception while fetch from firestore", e);
		}
		return data;
	}
}
