/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avbravo.jmoordbgenesis.microprofile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.StreamSupport;
import org.eclipse.microprofile.config.Config;

/**
 *
 * @author avbravo
 */
public enum MicroprofileConfigFile {
    
    

        
    /**
	 * The base directory of the storage in the file system. Default is "storage" in the working directory.
	 */
            DATABASE_USERNAME(
		Constants.PREFIX + "database.username",
		ConectionConfigurationProperties.DATABASE_USERNAME
	),
	
            DATABASE_HOST(
		Constants.PREFIX + "database.host",
		ConectionConfigurationProperties.DATABASE_HOST
	),
            DATABASE_NAME(
		Constants.PREFIX + "database.name",
		ConectionConfigurationProperties.DATABASE_NAME
	),
            DATABASE_PASSWORD(
		Constants.PREFIX + "database.password",
		ConectionConfigurationProperties.DATABASE_PASSWORD
	),
            DATABASE_PORT(
		Constants.PREFIX + "database.port",
		ConectionConfigurationProperties.DATABASE_PORT
	),
	
            DATABASE_SECURITY(
		Constants.PREFIX + "database.security",
		ConectionConfigurationProperties.DATABASE_SECURITY
	),
	
          
	
	
	
	/**
	 * Allow custom properties in through Microprofile, using this prefix. E.g.:
	 * If you want to include the "custom.test" property, you will set it as "one.microstream.property.custom.test"
	 */
	CUSTOM(
			Constants.PREFIX + "property",
		""
	);

	private final String microprofile;
	private final String jmoordbcore ;
	
	MicroprofileConfigFile(final String microprofile, final String jmoordbcore)
	{
		this.microprofile = microprofile;
		this.jmoordbcore  = jmoordbcore ;
	}
	
	public String getMicroprofile()
	{
		return this.microprofile;
	}

    public String getJmoordbcore() {
        return jmoordbcore;
    }
	
	
	
	/**
	 * Check if there is a relation between the Microstream and Microstream properties.
	 * If not, it is because it is a custom property.
	 *
	 * @return true if miscrostream is {@link String#isBlank()}
	 */
	public boolean isCustom()
	{
		return this.jmoordbcore.isBlank();
	}
	
	/**
	 * Return true if there is a relation between the Microstream and Microstream properties.
	 *
	 * @return the positive of {@link ConfigurationCoreProperties#isCustom()}
	 */
	boolean isMapped()
	{
		return !this.isCustom();
	}
	
	public static Map<String, String> getProperties(final Config config)
	{
		final Map<String, String> properties = new HashMap<>();
		
		Arrays.stream(MicroprofileConfigFile.values())
			.filter(MicroprofileConfigFile::isMapped)
			.forEach(p -> addProperty(config, properties, p))
		;
		
		StreamSupport.stream(config.getPropertyNames().spliterator(), false)
			.filter(k -> k.contains(CUSTOM.getMicroprofile()))
			.forEach(k ->
			{
				final String key   = k.split(CUSTOM.getMicroprofile() + ".")[1];
				final String value = config.getValue(k, String.class);
				properties.put(key, value);
			})
		;
		
		return properties;
	}
	
	private static void addProperty(
		final Config                      config    ,
		final Map<String, String>         properties,
		final MicroprofileConfigFile property
	)
	{
		config.getOptionalValue(property.getMicroprofile(), String.class)
			.ifPresent(v -> properties.put(property.getJmoordbcore(), v))
		;
	}

	private static class Constants
	{
		static final String PREFIX = "com.jmoordbcore.";
	}
}
