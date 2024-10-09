import {PasswordValidator} from "./password.validator";
import {FormControl} from "@angular/forms";

describe('PasswordValidator', () => {
  it('Should validate a string password', () => {
    const control = new FormControl('Valid1@Password');
    const result = PasswordValidator.strong(control);
    expect(result).toBeNull();
  });

  it('should return an error for a password without uppercase letters', () => {
    const control = new FormControl('invalid1@password');
    const result = PasswordValidator.strong(control);
    expect(result).toEqual({
      strong: 'Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial.'
    });
  });

  it('should return an error for a password without lowercase letters', () => {
    const control = new FormControl('INVALID1@PASSWORD');
    const result = PasswordValidator.strong(control);
    expect(result).toEqual({
      strong: 'Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial.'
    });
  });

  it('should return an error for a password without numbers', () => {
    const control = new FormControl('Invalid@Password');
    const result = PasswordValidator.strong(control);
    expect(result).toEqual({
      strong: 'Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial.'
    });
  });

  it('should return an error for a password without special characters', () => {
    const control = new FormControl('Invalid1Password');
    const result = PasswordValidator.strong(control);
    expect(result).toEqual({
      strong: 'Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial.'
    });
  });

  it('should return an error for a password that is too short', () => {
    const control = new FormControl('Short1!');
    const result = PasswordValidator.strong(control);
    expect(result).toEqual({
      strong: 'Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial.'
    });
  });
})
