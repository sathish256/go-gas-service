package com.connectgas.app.fb.service;

import java.util.concurrent.ExecutionException;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

public interface FirebaseService<E, ID> {

	public E save(E entity);

	public E fetchById(ID id) throws InterruptedException, ExecutionException;

	public void deleteById(ID id) throws InterruptedException, ExecutionException;

	public String getCollectionName();

	default Firestore getFirestore() {
		return FirestoreClient.getFirestore();
	}

}
