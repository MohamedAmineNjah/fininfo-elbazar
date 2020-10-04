import React from 'react';
import { Switch } from 'react-router-dom';
import Loadable from 'react-loadable';

import Login from './modules/login/login';
import Register from './modules/account/register/register';
import Products from './modules/products/products';
import Activate from './modules/account/activate/activate';
import PasswordResetInit from './modules/account/password-reset/init/password-reset-init';
import PasswordResetFinish from './modules/account/password-reset/finish/password-reset-finish';
import Logout from './modules/login/logout';
import Home from './modules/home/home';
import Panier from './modules/panier/panier';
import Entities from './entities';
import PrivateRoute from './shared/auth/private-route';
import ErrorBoundaryRoute from './shared/error/error-boundary-route';
import PageNotFound from './shared/error/page-not-found';
import { AUTHORITIES } from './config/constants';
import  ProduitDetail  from './modules/products/product.detail';

const Account = Loadable({
  loader: () => import(/* webpackChunkName: "account" */ './modules/account'),
  loading: () => <div>loading ...</div>,
});

const Admin = Loadable({
  loader: () => import(/* webpackChunkName: "administration" */ './modules/administration'),
  loading: () => <div>loading ...</div>,
});

const Routes = () => (
  <div className="view-routes">
    <Switch>
      <ErrorBoundaryRoute path="/login" component={Login} />
      <ErrorBoundaryRoute path="/logout" component={Logout} />
      <ErrorBoundaryRoute path="/account/register" component={Register} />
      <ErrorBoundaryRoute path="/panier/" component={Panier} />
      <ErrorBoundaryRoute path="/products/:id" component={Products} />
      <ErrorBoundaryRoute path="/product/:id"  component={ProduitDetail} />
      <ErrorBoundaryRoute path="/account/activate/:key?" component={Activate} />
      <ErrorBoundaryRoute path="/account/reset/request" component={PasswordResetInit} />
      <ErrorBoundaryRoute path="/account/reset/finish/:key?" component={PasswordResetFinish} />
      <PrivateRoute path="/admin" component={Admin} hasAnyAuthorities={[AUTHORITIES.ADMIN]} />
      <PrivateRoute path="/account" component={Account} hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.USER]} />
      <ErrorBoundaryRoute path="/" exact component={Home} />
      <PrivateRoute path="/" component={Entities} hasAnyAuthorities={[AUTHORITIES.USER]} />
      <ErrorBoundaryRoute component={PageNotFound} />
    </Switch>
  </div>
);

export default Routes;
