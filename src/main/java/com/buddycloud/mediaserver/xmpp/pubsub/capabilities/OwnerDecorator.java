package com.buddycloud.mediaserver.xmpp.pubsub.capabilities;


public class OwnerDecorator extends AbstractCapabilitiesDecorator {

	private static final String TYPE = "owner";
	
	
	@Override
	public String getType() {
		return TYPE;
	}
}
