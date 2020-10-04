import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISlides, defaultValue } from 'app/shared/model/slides.model';

export const ACTION_TYPES = {
  SEARCH_SLIDES: 'slides/SEARCH_SLIDES',
  FETCH_SLIDES_LIST: 'slides/FETCH_SLIDES_LIST',
  FETCH_SLIDES: 'slides/FETCH_SLIDES',
  CREATE_SLIDES: 'slides/CREATE_SLIDES',
  UPDATE_SLIDES: 'slides/UPDATE_SLIDES',
  DELETE_SLIDES: 'slides/DELETE_SLIDES',
  SET_BLOB: 'slides/SET_BLOB',
  RESET: 'slides/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISlides>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type SlidesState = Readonly<typeof initialState>;

// Reducer

export default (state: SlidesState = initialState, action): SlidesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_SLIDES):
    case REQUEST(ACTION_TYPES.FETCH_SLIDES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SLIDES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SLIDES):
    case REQUEST(ACTION_TYPES.UPDATE_SLIDES):
    case REQUEST(ACTION_TYPES.DELETE_SLIDES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_SLIDES):
    case FAILURE(ACTION_TYPES.FETCH_SLIDES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SLIDES):
    case FAILURE(ACTION_TYPES.CREATE_SLIDES):
    case FAILURE(ACTION_TYPES.UPDATE_SLIDES):
    case FAILURE(ACTION_TYPES.DELETE_SLIDES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_SLIDES):
    case SUCCESS(ACTION_TYPES.FETCH_SLIDES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SLIDES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SLIDES):
    case SUCCESS(ACTION_TYPES.UPDATE_SLIDES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SLIDES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.SET_BLOB: {
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType,
        },
      };
    }
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/slides';
const apiSearchUrl = 'api/_search/slides';

// Actions

export const getSearchEntities: ICrudSearchAction<ISlides> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_SLIDES,
  payload: axios.get<ISlides>(`${apiSearchUrl}?query=${query}`),
});

export const getEntities: ICrudGetAllAction<ISlides> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SLIDES_LIST,
  payload: axios.get<ISlides>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ISlides> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SLIDES,
    payload: axios.get<ISlides>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISlides> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SLIDES,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISlides> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SLIDES,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISlides> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SLIDES,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType,
  },
});

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
