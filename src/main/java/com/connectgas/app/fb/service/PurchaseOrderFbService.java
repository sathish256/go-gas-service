package com.connectgas.app.fb.service;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.connectgas.app.fb.service.FirebaseService;
import com.connectgas.app.model.order.PurchaseOrder;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class PurchaseOrderFbService implements FirebaseService<PurchaseOrder, String> {

	@Override
	public PurchaseOrder save(PurchaseOrder purchaseOrder) {
		getFirestore().collection(getCollectionName()).document(purchaseOrder.getId()).set(purchaseOrder);
		return purchaseOrder;
	}

	@Override
	public PurchaseOrder fetchById(String purchaseOrderId) throws InterruptedException, ExecutionException {

		DocumentReference docRef = getFirestore().collection(getCollectionName()).document(purchaseOrderId);
		// asynchronously retrieve the document
		ApiFuture<DocumentSnapshot> future = docRef.get();
		// block on response
		DocumentSnapshot document = future.get();
		PurchaseOrder person = null;
		if (document.exists()) {
			// convert document to POJO
			person = document.toObject(PurchaseOrder.class);
			System.out.println(person);
			return person;
		} else {
			System.out.println("No such document!");
			return null;
		}
	}

	@Override
	public void deleteById(String purchaseOrderId) throws InterruptedException, ExecutionException {

		Firestore db = FirestoreClient.getFirestore();
		ApiFuture<WriteResult> writeResult = db.collection(getCollectionName()).document(purchaseOrderId).delete();
		System.out.println(writeResult.get().getUpdateTime().toString());
	}

	@Override
	public String getCollectionName() {
		return PurchaseOrder.class.getSimpleName().toLowerCase();
	}
}
