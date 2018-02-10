package org.dselent.scheduling.server.dto;


import javax.annotation.Generated;

public class EditInstructorDto {
    private final Integer instructorId;
    private final String rank;
    private final String firstname;
    private final String lastname;
    private final String email;
    private final Boolean deleted;

    @Generated("SparkTools")
    private EditInstructorDto(EditInstructorDto.Builder builder)
    {
        this.instructorId = builder.instructorId;
        this.rank = builder.rank;
        this.firstname = builder.firstname;
        this.lastname = builder.lastname;
        this.email = builder.email;
        this.deleted = builder.deleted;

        if (this.instructorId == null)
        {
            throw new IllegalStateException("instructorId cannot be null");
        }
        else if (this.rank == null)
        {
            throw new IllegalStateException("rank cannot be null");
        }
        else if (this.firstname == null)
        {
            throw new IllegalStateException("firstname cannot be null");
        }
        else if (this.lastname == null)
        {
            throw new IllegalStateException("lastname cannot be null");
        }
        else if (this.email == null)
        {
            throw new IllegalStateException("email cannot be null");
        }
        else if (this.deleted == null)
        {
            throw new IllegalStateException("deleted cannot be null");
        }
    }

    public Integer getinstructorId() {
        return instructorId;
    }

    public String getrank() {
        return rank;
    }

    public String getfirstname() {
        return firstname;
    }

    public String getlastname() {
        return lastname;
    }
    
    public String getemail() {
        return email;
    }

    public Boolean getDeleted() {
        return deleted;
    }


    @Generated("SparkTools")
    public static EditInstructorDto.Builder builder()
    {
        return new EditInstructorDto.Builder();
    }

    /**
     * Builder to build {@link EditInstructorDto}.
     */
    @Generated("SparkTools")
    public static final class Builder
    {
        private Integer instructorId;
        private String rank;
        private String firstname;
        private String lastname;
        private String email;
        private Boolean deleted;

        private Builder()
        {
        }

        public Builder withinstructorId( Integer instructorId)
        {
            this.instructorId = instructorId;
            return this;
        }

        public Builder withrank( String rank)
        {
            this.rank = rank;
            return this;
        }

        public Builder withfirstname( String firstname)
        {
            this.firstname = firstname;
            return this;
        }

        public Builder withlastname( String lastname)
        {
            this.lastname = lastname;
            return this;
        }

        public Builder withemail( String email)
        {
            this.email = email;
            return this;
        }

        public Builder withDeleted( Boolean deleted)
        {
            this.deleted = deleted;
            return this;
        }

        public EditInstructorDto build()
        {
            return new EditInstructorDto(this);
        }
    }
}
