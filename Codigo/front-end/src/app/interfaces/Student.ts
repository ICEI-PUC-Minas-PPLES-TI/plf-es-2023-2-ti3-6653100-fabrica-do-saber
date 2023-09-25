export interface Student {
    guardians: {
        fullName: string;
        cpf: string;
        rg: string;
        email: string;
        occupation: string;
        company: string;
        phoneNumber: string;
        streetAddress: string;
        addressNumber: string;
        neighborhood: string;
        cityOfResidence: string;
        zipCode: string;
    }[];
    id: number;
    fullName: string;
    registrationDate: string;
    grade: string;
    birthDate: string;
    hometown: string;
    homeState: string;
    nationality: string;
    religion: string;
    race: string;
    streetAddress: string;
    addressNumber: string;
    neighborhood: string;
    cityOfResidence: string;
    zipCode: string;
}
