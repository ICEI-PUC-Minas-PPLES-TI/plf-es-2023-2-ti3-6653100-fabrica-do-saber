import { Parent } from './Parent';

export interface Student {
  parents: Parent[];
  teamIds: number[]
  id: number;
  fullName: string;
  registrationDate: string;
  birthDate: string;
  hometown: string;
  homeState: string;
  nationality: string;
  religion: string;
  race: string;
  streetAddress: string;
  addressNumber: string;
  addressComplement: string;
  neighborhood: string;
  cityOfResidence: string;
  zipCode: string;
}
