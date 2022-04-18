
package com.avbravo.jmoordbgenesis.type;


public enum StoreType
{
	/**
	 * Lazy storing is the default storing mode of the MicroStream engine.
	 * Referenced instances are stored only if they have not been stored yet.
	 * If a referenced instance has been stored previously it is not stored again even if it has been modified.
	 * It will use the method {@link one.microstream.storage.types.StorageManager#store}
	 * E.g.: The list of products: storageManager.store(inventory.getProducts());
	 */
	LAZY,
	/**
	 * In eager storing mode referenced instances are stored even if they had been stored before.
	 * Contrary to Lazy storing this will also store modified child objects at the cost of performance.
	 * It will {@link one.microstream.storage.types.StorageManager#createEagerStorer}.
	 * E.g.:
	 * Storer storer = storage.createEagerStorer();
	 * storer.store(inventory.getProducts());
	 * storer.commit();
	 */
	EAGER;
}
