import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from '../../../shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from '../../../shared/reducers/action-type.util';

import { IAdresse, defaultValue } from '../../../shared/model/adresse.model';

export const ACTION_TYPES = {
  SEARCH_ADRESSES: 'adresse/SEARCH_ADRESSES',
  FETCH_ADRESSE_LIST: 'adresse/FETCH_ADRESSE_LIST',
  FETCH_ADRESSE: 'adresse/FETCH_ADRESSE',
  CREATE_ADRESSE: 'adresse/CREATE_ADRESSE',
  UPDATE_ADRESSE: 'adresse/UPDATE_ADRESSE',
  DELETE_ADRESSE: 'adresse/DELETE_ADRESSE',
  RESET: 'adresse/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [
    {
      id: 1151,
      principale: true,
      prenom: 'Achraf',
      nom: 'Bouchiba',
      adresse: "11, rue de l'étoile",
      gouvernorat: 'Tunis',
      ville: 'La Marsa',
      localite: 'Cité Sprols',
      indication: null,
      telephone: 25091079,
      mobile: 25091079,
      creeLe: '2020-06-05',
      creePar: 'anonymousUser',
      modifieLe: '2020-06-05',
      modifiePar: 'anonymousUser',
      clientId: 1101,
      zoneId: null,
      zoneNom: null,
      codePostalId: null,
      codePostalCodePostal: null,
    },
    {
      id: 3451,
      principale: false,
      prenom: 'Achraf',
      nom: 'Bouchiba',
      adresse: "11, rue de l'étoile",
      gouvernorat: 'Tunis',
      ville: 'Tunis',
      localite: 'CIté les palmiers',
      indication: 'Derrière l’église',
      telephone: null,
      mobile: 25091079,
      creeLe: null,
      creePar: null,
      modifieLe: null,
      modifiePar: null,
      clientId: 1101,
      zoneId: null,
      zoneNom: null,
      codePostalId: null,
      codePostalCodePostal: null,
    },
  ] as ReadonlyArray<IAdresse>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type AdresseState = Readonly<typeof initialState>;

// Reducer

export default (state: AdresseState = initialState, action): AdresseState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_ADRESSES):
    case REQUEST(ACTION_TYPES.FETCH_ADRESSE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ADRESSE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ADRESSE):
    case REQUEST(ACTION_TYPES.UPDATE_ADRESSE):
    case REQUEST(ACTION_TYPES.DELETE_ADRESSE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_ADRESSES):
    case FAILURE(ACTION_TYPES.FETCH_ADRESSE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ADRESSE):
    case FAILURE(ACTION_TYPES.CREATE_ADRESSE):
    case FAILURE(ACTION_TYPES.UPDATE_ADRESSE):
    case FAILURE(ACTION_TYPES.DELETE_ADRESSE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_ADRESSES):
    case SUCCESS(ACTION_TYPES.FETCH_ADRESSE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_ADRESSE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ADRESSE):
    case SUCCESS(ACTION_TYPES.UPDATE_ADRESSE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ADRESSE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/adresses';
const apiSearchUrl = 'api/_search/adresses';

// Actions

export const getSearchEntities: ICrudSearchAction<IAdresse> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_ADRESSES,
  payload: axios.get<IAdresse>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IAdresse> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ADRESSE_LIST,
    payload: axios.get<IAdresse>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IAdresse> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ADRESSE,
    payload: axios.get<IAdresse>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAdresse> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ADRESSE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAdresse> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ADRESSE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAdresse> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ADRESSE,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
