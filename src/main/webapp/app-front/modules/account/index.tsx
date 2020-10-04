import React from 'react';

import ErrorBoundaryRoute from '../../shared/error/error-boundary-route';

import Settings from './profil-main/profil-main';
import Password from './password/password';

const Routes = ({ match }) => (
  <div>
    <ErrorBoundaryRoute path={`${match.url}/profil-main`} component={Settings} />
    <ErrorBoundaryRoute path={`${match.url}/password`} component={Password} />
  </div>
);

export default Routes;
