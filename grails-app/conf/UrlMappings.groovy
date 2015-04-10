class UrlMappings {

	static mappings = {
		"/$controller/$ids?"{
			action = [GET:"show", POST:"save", PUT:"update", DELETE:"remove"]
		  }
		
	}
}
