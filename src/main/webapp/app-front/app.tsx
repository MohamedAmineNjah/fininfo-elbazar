import 'react-toastify/dist/ReactToastify.css';
import './app.scss';

import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Card, Col, Row } from 'reactstrap';
import { BrowserRouter as Router } from 'react-router-dom';
import { ToastContainer, toast } from 'react-toastify';
import { hot } from 'react-hot-loader';
import { Storage } from 'react-jhipster';

import { IRootState } from './shared/reducers';
import { getSession } from './shared/reducers/authentication';
import { getProfile } from './shared/reducers/application-profile';
import { getCategories } from './shared/reducers/category.reducer';
import { getinfosociete } from './shared/reducers/home.reducer';
import { setLocale } from './shared/reducers/locale';
import Header from './shared/layout/header/header';
import SideMenu from './shared/layout/sideMenu/sideMenu';
import Footer from './shared/layout/footer/footer';
import { hasAnyAuthority } from './shared/auth/private-route';
import ErrorBoundary from './shared/error/error-boundary';
import { AUTHORITIES } from './config/constants';
import AppRoutes from './routes';

const baseHref = document.querySelector('base').getAttribute('href').replace(/\/$/, '');

export interface IAppProps extends StateProps, DispatchProps {}

export const App = (props: IAppProps) => {
  if (process.env.NODE_ENV === 'production') {
    console.log('%cStop!', 'color: red; font-family: sans-serif; font-size: 4.5em; font-weight: bolder; text-shadow: #000 1px 1px;');
    console.log(
      '%cAcces interdit!',
      'color: black; font-family: sans-serif; font-size: 3em; font-weight: bolder; text-shadow: #000 1px 1px;'
    );
    console.log(
      '%cIl s’agit d’une fonctionnalité de navigateur conçue pour les développeurs. \n Si quelqu’un vous a invité(e) à copier-coller quelque chose ici pour activer une fonctionnalité ou soit-disant pirater le bazar,\n  sachez que c’est une escroquerie permettant à cette personne d’accéder à votre compte Elbazar.',
      'color: black; font-family: sans-serif; font-size: 1em; font-weight: bold;'
    );
    console.log(
      '%c ',
      'font-size:460px; background:url(https://www.icône.com/images/icones/9/2/pictograms-aem-0124-do-not-put-hand-in-this-area.png) no-repeat;'
    );

    console.log = function () {};
    console.error = function () {};
  }
  useEffect(() => {
    if (Storage.local.get('jhi-authenticationToken') || Storage.session.get('jhi-authenticationToken')) {
      props.getSession();
    }
    props.getProfile();
    props.getCategories();
  }, []);
  useEffect(() => {}, [props.switchNav]);
  const paddingTop = '60px';
  return (
    <Router basename={baseHref}>
      <div className="app-container" style={{ paddingTop }}>
        <ToastContainer position={toast.POSITION.TOP_LEFT} className="toastify-container" toastClassName="toastify-toast" />
        <ErrorBoundary>
          <Header
            isAuthenticated={props.isAuthenticated}
            isAdmin={props.isAdmin}
            currentLocale={props.currentLocale}
            onLocaleChange={props.setLocale}
            ribbonEnv={props.ribbonEnv}
            isInProduction={props.isInProduction}
            isSwaggerEnabled={props.isSwaggerEnabled}
          />
        </ErrorBoundary>
        <SideMenu isAuthenticated={props.isAuthenticated} isAdmin={props.isAdmin} switchnav={props.switchNav} />
      </div>
      <div
        className={
          props.switchNav ? 'container-fluid view-container container-fluid-show' : 'container-fluid view-container container-fluid-hide'
        }
        id="app-view-container"
      >
        <Card className="jh-card">
          <ErrorBoundary>
            <AppRoutes />
          </ErrorBoundary>
        </Card>
        <Footer />
      </div>
    </Router>
  );
};

const mapStateToProps = ({ authentication, categories, applicationProfile, locale }: IRootState) => ({
  currentLocale: locale.currentLocale,
  isAuthenticated: authentication.isAuthenticated,
  isAdmin: hasAnyAuthority(authentication.account.authorities, [AUTHORITIES.ADMIN]),
  ribbonEnv: applicationProfile.ribbonEnv,
  isInProduction: applicationProfile.inProduction,
  isSwaggerEnabled: applicationProfile.isSwaggerEnabled,
  switchNav: categories.showedSidNav,
});

const mapDispatchToProps = { setLocale, getSession, getProfile, getCategories, getinfosociete };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(hot(module)(App));
