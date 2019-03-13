/*******************************************************************************
 * Copyright (c) 2019 Bosch Software Innovations GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 *
 * The Eclipse Public License is available at
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 *    http://www.eclipse.org/org/documents/edl-v10.html.
 *
 * Contributors:
 *    Bosch Software Innovations GmbH - initial implementation
 *******************************************************************************/

package org.eclipse.californium.scandium.dtls.pskstore;

import java.net.InetSocketAddress;

import org.eclipse.californium.scandium.dtls.PskPublicInformation;
import org.eclipse.californium.scandium.util.ServerNames;

/**
 * A mapped bytes base pre-shared-key store.
 * <p>
 * Uses a UTF-8 string based store.
 */
public class MappedBytesPskStore implements BytesPskStore {

	private final PskStore pskStore;

	public MappedBytesPskStore(PskStore pskStore) {
		if (pskStore == null) {
			throw new NullPointerException("psk store must not be null!");
		}
		this.pskStore = pskStore;
	}

	@Override
	public PskPublicInformation getIdentity(final InetSocketAddress inetAddress) {
		String identity = pskStore.getIdentity(inetAddress);
		return identity == null ? null : new PskPublicInformation(identity);
	}

	@Override
	public PskPublicInformation getIdentity(InetSocketAddress peerAddress, ServerNames virtualHost) {
		String identity = pskStore.getIdentity(peerAddress, virtualHost);
		return identity == null ? null : new PskPublicInformation(identity);
	}

	@Override
	public byte[] getKey(final PskPublicInformation identity) {
		if (identity.isCompliantEncoding()) {
			return pskStore.getKey(identity.getPublicInfoAsString());
		}
		return null;
	}

	@Override
	public byte[] getKey(final ServerNames serverNames, final PskPublicInformation identity) {
		if (identity.isCompliantEncoding()) {
			return pskStore.getKey(serverNames, identity.getPublicInfoAsString());
		}
		return null;
	}
}
