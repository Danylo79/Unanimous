export class User {
    public username: string;
    public homeroom: string;
    public studentID: number;

    constructor(username: string, homeroom: string, studentID: number) {
        this.username = username;
        this.homeroom = homeroom;
        this.studentID = studentID;
    }
}