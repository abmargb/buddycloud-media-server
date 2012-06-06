package com.buddycloud.mediaserver.business.dao;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.buddycloud.mediaserver.business.jdbc.MetadataSource;
import com.buddycloud.mediaserver.commons.Constants;
import com.buddycloud.mediaserver.commons.exception.InvalidPreviewFormatException;
import com.buddycloud.mediaserver.commons.exception.MediaMetadataSourceException;
import com.buddycloud.mediaserver.commons.exception.MediaNotFoundException;

public class MediasDAO extends DAO {

	private static Logger LOGGER = Logger.getLogger(MediasDAO.class);


	MediasDAO(MetadataSource dataSource, Properties configuration) {
		super(dataSource, configuration);
	}

	
	public File getMedia(String entityId, String mediaId) 
			throws MediaMetadataSourceException, MediaNotFoundException, IOException, InvalidPreviewFormatException {
		//TODO authentication
		
		LOGGER.debug("Getting media. Media ID: " + mediaId);
		
		String fullDirectoryPath = getDirectory(entityId);
		File media = new File(fullDirectoryPath + File.separator + mediaId);

		if (!media.exists()) {
			throw new MediaNotFoundException(mediaId, entityId);
		}

		// Update last viewed date
		dataSource.updateMediaLastViewed(mediaId);
		
		return media;
	}

	public byte[] getMediaPreview(String entityId, String mediaId, Integer maxHeight, Integer maxWidth) 
			throws MediaMetadataSourceException, MediaNotFoundException, IOException, InvalidPreviewFormatException {
		//TODO authentication
		
		LOGGER.debug("Getting media preview. Media ID: " + mediaId);
		
		return getPreview(entityId, mediaId, maxHeight, maxWidth, getDirectory(entityId));
	}

	public String getMediaType(String mediaId) throws MediaMetadataSourceException {
		return dataSource.getMediaMimeType(mediaId);
	}

	protected String getDirectory(String entityId) {
		return configuration.getProperty(Constants.MEDIA_STORAGE_ROOT_PROPERTY) +
				File.separator + entityId;
	}
}