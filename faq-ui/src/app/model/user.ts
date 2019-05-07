export class User {
    name = '';
    roles: string[] = [];

    isAdmin() {
        return this.roles && this.roles.filter(role => role === 'ROLE_ADMIN').length === 1;
    }
}
