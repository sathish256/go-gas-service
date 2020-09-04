package com.connectgas.app.repository;

import java.util.List;
import java.util.Optional;

import com.connectgas.app.exception.ConnectGasDataAccessException;
import com.connectgas.app.model.common.ConnectGasEntity;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

public interface FirestoreRepository<E extends ConnectGasEntity, ID> {

	public E save(E entity, String collection);

	public Optional<E> fetchById(ID id, String collection, Class<E> persistentClass)
			throws ConnectGasDataAccessException;

	public void deleteById(ID id, String collection) throws ConnectGasDataAccessException;

	public List<E> findAll(String collection, Class<E> persistentClass);

	public List<E> findByPathAndValue(String path, String value, String collection, Class<E> persistentClass);

	default Firestore getFirestore() {
		return FirestoreClient.getFirestore();
	}

}
