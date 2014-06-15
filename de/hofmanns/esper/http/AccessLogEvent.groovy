package de.hofmanns.esper.http

class AccessLogEvent {
	private String ipAddress;
	
		private String page;
	
		private String date;
	
		public AccessLogEvent() {
		}
	
		public AccessLogEvent(String ipAddress, String page, String date) {
			this.ipAddress = ipAddress;
			this.page = page;
			this.date = date;
		}
	
		public String getIpAddress() {
			return ipAddress;
		}
	
		public void setIpAddress(String ipAddress) {
			this.ipAddress = ipAddress;
		}
	
		public String getPage() {
			return page;
		}
	
		public void setPage(String page) {
			this.page = page;
		}
	
		public String getDate() {
			return date;
		}
	
		public void setDate(String date) {
			this.date = date;
		}
	
		@Override
		public String toString() {
			return "AccessLogEvent{" +
					"ipAddress='" + ipAddress + '\'' +
					", page='" + page + '\'' +
					", date='" + date + '\'' +
					'}';
		} 

}
