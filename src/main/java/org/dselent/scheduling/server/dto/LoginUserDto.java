package org.dselent.scheduling.server.dto;

import javax.annotation.Generated;

public class LoginUserDto {
    private final String userName;
    private final String password;
    //private final Boolean deleted;

    @Generated("SparkTools")
    private LoginUserDto(LoginUserDto.Builder builder)
    {
        this.userName = builder.userName;
        this.password = builder.password;

        if (this.userName == null)
        {
            throw new IllegalStateException("userName cannot be null");
        } 
        else if (this.password == null)
        {
            throw new IllegalStateException("password cannot be null");
        }
    }

    public String getuserName() {
        return userName;
    }

    public String getpassword() {
        return password;
    }


    @Generated("SparkTools")
    public static LoginUserDto.Builder builder()
    {
        return new LoginUserDto.Builder();
    }

    /**
     * Builder to build {@link LoginUserDto}.
     */
    @Generated("SparkTools")
    public static final class Builder
    {
        private String userName;
        private String password;

        private Builder()
        {
        }

        public LoginUserDto.Builder withuserName( String userName)
        {
            this.userName = userName;
            return this;
        }

        public LoginUserDto.Builder withpassword( String password)
        {
            this.password = password;
            return this;
        }

        public LoginUserDto build()
        {
            return new LoginUserDto(this);
        }
    }
}
