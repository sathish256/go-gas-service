package com.connectgas.app.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.connectgas.app.exception.ConnectGasDataAccessException;
import com.connectgas.app.model.common.ConnectGasEntity;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;

@Repository
public class SimpleFirestoreRepository<E extends ConnectGasEntity, ID> implements FirestoreRepository<E, ID> {

	@Override
	@Cacheable(value = "#collection")
	public E save(E entity, String collection) {
		getFirestore().collection(entity.getClass().getSimpleName().toLowerCase()).document(entity.getId()).set(entity);
		return entity;
	}

	@Override
	@Cacheable(value = "#collection")
	public Optional<E> fetchById(ID id, String collection, Class<E> persistentClass)
			throws ConnectGasDataAccessException {
		DocumentReference docRef = getFirestore().collection(collection).document((String) id);
		// asynchronously retrieve the document
		ApiFuture<DocumentSnapshot> future = docRef.get();
		// block on response
		DocumentSnapshot document;
		E entity = null;
		try {
			document = future.get();

			if (document.exists()) {
				entity = document.toObject(persistentClass);
			}
		} catch (InterruptedException | ExecutionException e) {
			throw new ConnectGasDataAccessException("Exception while fetch from firestore", e);
		}

		return Optional.ofNullable(entity);
	}

	@Override
	@CacheEvict(value = "#collection")
	public void deleteById(ID id, String collection) throws ConnectGasDataAccessException {

		try {
			getFirestore().collection(collection).document((String) id).delete();
		} catch (Exception e) {
			throw new ConnectGasDataAccessException("Exception while fetch from firestore", e);
		}

	}

	@Override
	@Cacheable(value = "#collection")
	public List<E> findAll(String collection, Class<E> persistentClass) {
		List<E> data = new ArrayList<>();
		ApiFuture<QuerySnapshot> future = getFirestore().collection(collection).get();
		try {
			for (DocumentSnapshot document : future.get().getDocuments()) {
				data.add(document.toObject(persistentClass));
			}
		} catch (InterruptedException | ExecutionException e) {
			throw new ConnectGasDataAccessException("Exception while fetch from firestore", e);
		}

		return data;
	}

	@Override
	@Cacheable(value = "#collection")
	public List<E> findByPathAndValue(String path, String value, String collection, Class<E> persistentClass) {
		List<E> data = new ArrayList<>();
		CollectionReference cities = getFirestore().collection(collection);
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
