import { AbstractControl, ValidationErrors } from '@angular/forms';

export class PasswordValidator {

  static strong(control: AbstractControl): ValidationErrors | null {
    const value = control.value || '';

    const hasUpperCase = /[A-Z]/.test(value);
    const hasLowerCase = /[a-z]/.test(value);
    const hasNumeric = /[0-9]/.test(value);
    const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(value);

    const passwordValid = hasUpperCase && hasLowerCase && hasNumeric && hasSpecialChar;

    if (!passwordValid) {
      return {
        strong: 'Le mot de passe doit contenir une majuscule, une minuscule, un chiffre et un caractère spécial.'
      };
    }
    return null;
  }
}
