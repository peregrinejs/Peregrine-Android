package com.peregrinejs

import kotlinx.coroutines.flow.Flow

/**
 * A flow of events.
 */
typealias RemoteObservable = Flow<Event>

/**
 * A map of observable names -> observables.
 */
typealias RemoteObservables = Map<String, RemoteObservable>
