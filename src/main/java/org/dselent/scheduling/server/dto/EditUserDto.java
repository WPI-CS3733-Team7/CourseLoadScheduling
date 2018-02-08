package org.dselent.scheduling.server.dto;

import javax.annotation.Generated;

import org.dselent.scheduling.server.dto.RegisterUserDto.Builder;

public class EditUserDto {

	private final Integer editId;
	private final Integer userRole;
	private final Integer linkedInstructorId;
	private final Boolean deleted;
	
	@Generated("SparkTools")
	private EditUserDto(Builder builder)
	{
		this.editId = builder.editId;
		this.userRole = builder.userRole;
		this.linkedInstructorId = builder.linkedInstructorId;
		this.deleted = builder.deleted;

		if (this.editId == null)
		{
			throw new IllegalStateException("editId cannot be null");
		} else if (this.userRole == null)
		{
			throw new IllegalStateException("userRole cannot be null");
		}
		else if (this.deleted == null)
		{
			throw new IllegalStateException("deleted cannot be null");
		}
	}

	public Integer getEditId() {
		return editId;
	}

	public Integer getUserRole() {
		return userRole;
	}

	public Integer getLinkedInstructorId() {
		return linkedInstructorId;
	}

	public Boolean getDeleted() {
		return deleted;
	}
	
	
	@Generated("SparkTools")
	public static Builder builder()
	{
		return new Builder();
	}

	/**
	 * Builder to build {@link EditUserDto}.
	 */
	@Generated("SparkTools")
	public static final class Builder
	{
		private Integer editId;
		private Integer userRole;
		private Integer linkedInstructorId;
		private Boolean deleted;

		private Builder()
		{
		}

		public Builder withEditId(Integer editId)
		{
			this.editId = editId;
			return this;
		}

		public Builder withUserRole(Integer userRole)
		{
			this.userRole = userRole;
			return this;
		}

		public Builder withLinkedInstructorId(Integer linkedInstructorId)
		{
			this.linkedInstructorId = linkedInstructorId;
			return this;
		}

		public Builder withDeleted(Boolean deleted)
		{
			this.deleted = deleted;
			return this;
		}
		
		public EditUserDto build()
		{
			return new EditUserDto(this);
		}
	}
}
