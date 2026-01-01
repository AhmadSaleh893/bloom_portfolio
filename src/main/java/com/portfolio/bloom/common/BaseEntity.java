package com.portfolio.bloom.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Base entity class providing common fields for all domain entities.
 * Includes ID, timestamps, soft delete flag, and translation support.
 */
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(builderMethodName = "baseEntityBuilder")
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseEntity<ID> extends Translation implements Serializable {

	public static class Fields {
		public static String ID = "id";
		public static String CREATED = "created";
		public static String UPDATED = "updated";
		public static String DELETED = "deleted";
		public static String TRANSLATIONS = "translations";
	}
	
	@Id
	private ID id;
	
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@Builder.Default
	private long created = (new Date()).getTime();
	
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private long updated;
	
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@Builder.Default
	private boolean deleted = false;

	public void setCreated(Date created) { 
		this.created = created.getTime(); 
	}

	public void setUpdated(Date updated) { 
		this.updated = updated.getTime(); 
	}

	public void setCreated(long created) { 
		this.created = created; 
	}

	public void setUpdated(long updated) { 
		this.updated = updated; 
	}

	public void setDeleted(boolean deleted) { 
		this.deleted = deleted; 
	}

	public BaseEntity(ID id, long created, long updated, boolean deleted,
            Map<String, Map<String, String>> translations) {
        this.id = id;
        this.created = created;
        this.updated = updated;
        this.deleted = deleted;
        this.setTranslations(translations);
    }
}
