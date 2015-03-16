package stockcontrol

import grails.transaction.Transactional

import stockcontrol.ShiroUser;
import org.apache.shiro.crypto.hash.Sha256Hash
import org.apache.shiro.subject.Subject
import org.apache.shiro.SecurityUtils

@Transactional
class UserService {
	UserValidatorService userValidatorService = new UserValidatorService()
	
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
		object = userValidatorService.createObjectValidation(newObject)
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
		object = userValidatorService.updateObjectValidation(newObject)
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
		object = userValidatorService.updatePasswordObjectValidation(object as ShiroUser, new Sha256Hash(oldpass).toHex(), new Sha256Hash(confirmpass).toHex())
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
	
}
