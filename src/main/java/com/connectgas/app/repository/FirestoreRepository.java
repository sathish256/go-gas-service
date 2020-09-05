package com.connectgas.app.repository;

import java.util.List;
import java.util.Optional;

import com.connectgas.app.exception.ConnectGasDataAccessException;
import com.connectgas.app.model.common.ConnectGasEntity;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

public interface FirestoreRepository<E extends ConnectGasEntity, ID> {

	public E save(E entity, Class<E> persistentClass);

	public Optional<E> fetchById(ID id, Class<E> persistentClass) throws ConnectGasDataAccessException;

	public void deleteById(ID id, Class<E> persistentClass) throws ConnectGasDataAccessException;

	public List<E> findAll(Class<E> persistentClass);

	@Deprecated
	public List<E> findByPathAndValue1(String path, String value, Class<E> persistentClass);

	default Firestore getFirestore() {
		return FirestoreClient.getFirestore();
	}

}
