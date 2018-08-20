package com.example.isa2017.modelDTO;

public class ChangePassDTO {

	private String old;
	private String newPass;
	private String newConfirmed;
	
	public ChangePassDTO(String old, String newPass, String newConfirmed) {
		super();
		this.old = old;
		this.newPass = newPass;
		this.newConfirmed = newConfirmed;
	}

	public ChangePassDTO() {
		super();
	}

	public String getOld() {
		return old;
	}

	public void setOld(String old) {
		this.old = old;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public String getNewConfirmed() {
		return newConfirmed;
	}

	public void setNewConfirmed(String newConfirmed) {
		this.newConfirmed = newConfirmed;
	}
	
	
	
}
