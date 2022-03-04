import { User } from "../data/user";

export class UserService {
    public getCurrentUser(): User {
        return new User("Danylo", "710", 14);
    }
}