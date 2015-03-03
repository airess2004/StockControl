package stockcontrol

import grails.transaction.Transactional

import stockcontrol.ShiroUser;
import org.apache.shiro.crypto.hash.Sha256Hash
import org.apache.shiro.subject.Subject
import org.apache.shiro.SecurityUtils

@Transactional
class UserService {
	//UserValidator userValidator = new UserValidator()
	
    def serviceMethod() {

    }
	
	def getObjectById(def object){
		return ShiroUser.get(object)
	}
	
	def getList(){
		return ShiroUser.getAll()
	}
	
	def createObject(object){
		ShiroUser newObject = new ShiroUser()
		newObject.username = String.valueOf(object.username).toUpperCase()
		newObject.passwordHash = new Sha256Hash(object.passwordHash).toHex()
		newObject.isDeleted = false
		object = createObjectValidation(newObject)
		if (object.errors.getErrorCount() == 0)
		{
			newObject.save()
			object = newObject
		} 
		return newObject
	}
	
	def updateObject(def object){
		def newObject = ShiroUser.read(object.id)
		newObject.username = String.valueOf(object.username).toUpperCase()
		newObject.passwordHash = new Sha256Hash(object.passwordHash).toHex()
		object = updateObjectValidation(newObject)
		if (object.errors.getErrorCount() == 0)
		{
			newObject.save()
			object = newObject
		}
		else newObject.discard()
		return object
	}
	
	def updatePasswordObject(def object, oldpass, confirmpass){
		def newObject = ShiroUser.read(object.id)
		object.passwordHash = new Sha256Hash(object.passwordHash).toHex()
		object = updatePasswordObjectValidation(object as ShiroUser, new Sha256Hash(oldpass).toHex(), new Sha256Hash(confirmpass).toHex())
		if (object.errors.getErrorCount() == 0)
		{
			newObject.passwordHash = object.passwordHash
			newObject.save()
			object = newObject
		} 
		//else object.discard()
		return object
	}
	
	def softDeleteObject(def object){
		def newObject = ShiroUser.get(object.id)
		newObject.isDeleted = true
		newObject.save()
	}
	
	// Validation
//	def nameNotNull(def object){
//		
//			if (object.username == null || object.username.trim() == "")
//			{
//				object.errors.rejectValue('username','null','UserName tidak boleh kosong')
//			}
//			return object
//		}
//	
//	def nameMustUnique(def object){
//		def uniq = ShiroUser.findByUsernameAndIsDeleted(object.username,false)
//		print uniq
//		print object
//		if (uniq != null)
//		{
//			if (uniq.id != object.id)
//			{
//			object.errors.rejectValue('username','null','UserName harus unik')
//			}
//		}
//		return object
//	}
//		
//	def passNotNull(def object){
//		
//			if (object.passwordHash == null || object.passwordHash.trim() == new Sha256Hash("").toHex())
//			{
//				object.errors.rejectValue('passwordHash','null','Password tidak boleh kosong')
//			}
//			return object
//		}
//	
//	def confirmPassCorrect(def object, confirmpass){
//		
//			if (object.passwordHash != confirmpass)
//			{
//				object.errors.rejectValue(null,'','Konfirmasi Password tidak sama')
//			}
//			return object
//		}
//	
//	def oldPassCorrect(def object, oldpass){
//			Subject currentUser = SecurityUtils.getSubject();
//			def user = ShiroUser.findByUsername(currentUser.getPrincipal())
//			if (oldpass != user.passwordHash)
//			{
//				object.errors.rejectValue(null,'','Old Password salah')
//			}
//			return object
//		}
//	
//	def createObjectValidation(def object){
//		object = nameNotNull(object)
//		if (object.errors.hasErrors()) return object
//		object = passNotNull(object)
//		return object
//	}
//	
//	def updateObjectValidation(def object){
//		object = createObjectValidation(object)
//		return object
//	}
//	
//	def updatePasswordObjectValidation(def object, oldpass, confirmpass){
//		object = createObjectValidation(object)
//		if (object.errors.hasErrors()) return object
//		object = confirmPassCorrect(object, confirmpass)
//		if (object.errors.hasErrors()) return object
//		object = oldPassCorrect(object, oldpass)
//		return object
//	}
}
