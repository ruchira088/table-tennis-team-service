package exceptions

import play.api.data.FormError

case class FormValidationError(formErrors: List[FormError]) extends Exception