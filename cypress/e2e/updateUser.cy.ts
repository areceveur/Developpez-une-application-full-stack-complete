describe('Update User', () => {
    it('Should login', () => {
        cy.clearLocalStorage();
        cy.visit('/auth/login');
        cy.get('input[formControlName=email]').type("user1@test.com");
        cy.get('input[formControlName=password]').type(`${"UserTest@1234"}`);
        cy.get('button[type="submit"]').click();
    });

    it('Should go in the user information', () => {
        cy.get(`.links > .mat-icon`).click();
    });

    it('Should update the username and the email', () => {
        cy.get('input[formControlName="username"]').clear();
        cy.get('input[formControlName="username"]').type("UserTest");
        cy.get('input[formControlName="newEmail"]').clear();
        cy.get('input[formControlName="newEmail"]').type("user@test.com");

        cy.get('form').first().submit();
    });

    it('should change the password', () => {
        cy.get('mat-label').contains('Mot de passe actuel').click();
        cy.get('input[formControlName="currentPassword"]').type("UserTest@1234");

        cy.get('mat-label').contains('Nouveau mot de passe').click();
        cy.get('input[formControlName="newPassword"]').type("NouveauMotDePasse@1234");

        cy.get('button').contains("Enregistrer").click();
    });
})