import {User} from '../../interfaces/User';

export class UserImp implements User {

  fullName: string;
  email: string;
  password: string;

  constructor() {
    this.fullName = '';
    this.email = '';
    this.password = '';
  }

}
