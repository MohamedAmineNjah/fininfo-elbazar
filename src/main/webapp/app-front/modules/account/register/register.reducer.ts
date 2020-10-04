import axios from 'axios';
import { translate } from 'react-jhipster';

import { REQUEST, SUCCESS, FAILURE } from '../../../shared/reducers/action-type.util';

export const ACTION_TYPES = {
  CREATE_ACCOUNT: 'register/CREATE_ACCOUNT',
  RESET: 'register/RESET',
  VERIFY_ADRESSE: 'register/VERIFY_ADRESSE',
};

const initialState = {
  loading: false,
  registrationSuccess: false,
  registrationFailure: false,
  errorMessage: null,
  loadingMail: false,
  mailExist: false,
};

export type RegisterState = Readonly<typeof initialState>;

// Reducer
export default (state: RegisterState = initialState, action): RegisterState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.CREATE_ACCOUNT):
      return {
        ...state,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.VERIFY_ADRESSE):
      return {
        ...state,
        loadingMail: true,
      };
    case FAILURE(ACTION_TYPES.VERIFY_ADRESSE):
      return {
        ...initialState,
        loadingMail: false,
        errorMessage: action.payload.response.data.errorKey,
        mailExist: true,
      };
    case SUCCESS(ACTION_TYPES.VERIFY_ADRESSE):
      return {
        ...initialState,
        loadingMail: false,
        mailExist: false,
      };
    case FAILURE(ACTION_TYPES.CREATE_ACCOUNT):
      return {
        ...initialState,
        registrationFailure: true,
        errorMessage: action.payload.response.data.errorKey,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ACCOUNT):
      return {
        ...initialState,
        registrationSuccess: true,
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

// Actions
export const handleRegister = (values, langKey = 'en') => ({
  type: ACTION_TYPES.CREATE_ACCOUNT,
  payload: axios.post('api/register', {
    id: null,
    login: values.email,
    firstName: values.firstName,
    lastName: values.lastName,
    email: values.email,
    createdDate: new Date(),
    authorities: ['ROLE_USER'],
    password: values.password,
    civilite: values.civilité,
    prenom: values.firstName,
    nom: values.lastName,
    dateDeNaissance: values.dateDeNaissance,
    mobile: values.tel,
    reglement: values.reglement,
    profile: 'Silver',
    principale: true,
    adresse: values.avenue,
    gouvernorat: values.gouvernorat,
    ville: values.ville,
    localite: values.localite,
    langKey,
  }),
});
export const verifyAdresse = email => ({
  type: ACTION_TYPES.VERIFY_ADRESSE,
  payload: axios.post('api/users/check', {
    emailUser: email,
  }),
});
export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
