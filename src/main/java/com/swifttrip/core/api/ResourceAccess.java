package com.swifttrip.core.api;

import java.util.List;

public class ResourceAccess {

	private Account account;

	private ResourceAccess() {
		//EMPTY
	}

	public Account getAccount() {
		return account;
	}

	public class Account{
		private List<String> roles;

		private Account(){
			//EMPTY
		}

		public List<String> getRoles() {
			return roles;
		}
	}
}
