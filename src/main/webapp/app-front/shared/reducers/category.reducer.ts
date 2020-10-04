import axios from 'axios';

import { SUCCESS } from './action-type.util';

export const ACTION_TYPES = {
  GET_CATEGORIES: 'categorie/GET_CATEGORIES',
  SWITCH_SIDE_NAV: 'categorie/SWITCH_SIDE_NAV',
};

const initialState = {
  listCategories: [],
  showedSidNav: true,
};

export type CategoryListState = Readonly<typeof initialState>;

export default (state: CategoryListState = initialState, action): CategoryListState => {
  switch (action.type) {
    case SUCCESS(ACTION_TYPES.GET_CATEGORIES): {
      return {
        ...state,
        listCategories: action.payload.data,
      };
    }
    case ACTION_TYPES.SWITCH_SIDE_NAV: {
      return {
        ...state,
        showedSidNav: action.payload,
      };
    }
    default:
      return state;
  }
};
export const switchSideNav = showedSidNav => {
  return {
    type: ACTION_TYPES.SWITCH_SIDE_NAV,
    payload: !showedSidNav,
  };
};
export const getCategories = () => ({
  type: ACTION_TYPES.GET_CATEGORIES,
  payload: axios.get('api/catalogue'),
});
