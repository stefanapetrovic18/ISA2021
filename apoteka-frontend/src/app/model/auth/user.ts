import {Role} from './role';

export class User {
  id: number;
  username: string;
  password: string;
  forename: string;
  surname: string;
  address: string;
  city: string;
  country: string;
  phone: string;
  enabled: boolean;
  validated: boolean;
  passwordChanged: boolean;
  roles: Role[];
}
