package com.portal.domen;

/* TODO : I would recommend to rename this enum to ServiceType */
public enum Service {
	HOT_WATER(1, "Hot water"),
	COLD_WATER(2, "Cold water"),
	ELECTRICITY(3, "Electricity"),;
	
	private int id;
	private String description;
	
	Service(int id, String description) {
		this.id = id;
		this.description = description;
    }
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public static Service valueOf(int id) {
		for(Service service: Service.values()){
			if(service.getId() == id){
				return service;
			}
		}
		return null;
	}
}
