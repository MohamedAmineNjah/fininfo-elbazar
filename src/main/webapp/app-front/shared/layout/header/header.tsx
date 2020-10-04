import './header.scss';

import React, { useState, useEffect } from 'react';
import { Translate, Storage } from 'react-jhipster';
import { Navbar, Nav, NavbarToggler, NavbarBrand, Collapse } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';


import { NavLink as Link, RouteComponentProps } from 'react-router-dom';
import LoadingBar from 'react-redux-loading-bar';

import { Home, Brand, Panier, ELBAZAR, MenuI } from './header-components';
import { AdminMenu, EntitiesMenu, AccountMenu, LocaleMenu } from '../menus';
import { switchSideNav } from '../../reducers/category.reducer';
import { connect } from 'react-redux';
import { IRootState } from '../../reducers';
import { getEntitieshome, reset } from '../../../modules/products/products.reducer';
import { Grid, Menu, Typography } from '@material-ui/core';
import { getinfosociete } from '../../reducers/home.reducer';
import MarkunreadIcon from '@material-ui/icons/Markunread';
import PermPhoneMsgIcon from '@material-ui/icons/PermPhoneMsg';
import PlaceIcon from '@material-ui/icons/Place';
import CGUModalComment from '../../../modules/CGU/CGU_modalcomment';







export interface IHeaderProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {

  isAuthenticated: boolean;
  isAdmin: boolean;
  ribbonEnv: string;
  isInProduction: boolean;
  isSwaggerEnabled: boolean;
  currentLocale: string;
  onLocaleChange: Function;
}

const Header = (props: IHeaderProps) => {
  // const { listSearchProduit } = props.reset();
  const [menuOpen, setMenuOpen] = useState(false);
  const [checkbox, handelSidNav] = useState(false);
  const [totalPrix, setTotalPrix] = useState<number>();// totalPrix comme un attribut w setTotalPrix : methode
  const [showModal, setShowModal] = React.useState(false);
  const [showModalFid, setShowModalFid] = React.useState(false);
  const handleClose = () => {
    setShowModal(false);
  };
  const handleCloseFid = () => {
    setShowModalFid(false);
  };
  const HandleShow = () => {
    setShowModal(true);
  };
  const HandleShowFid = () => {
    setShowModalFid(true);
  };

  useEffect(() => {
    props.getinfosociete();
  }, []);
  useEffect(() => { }, [props.listInformationsociete]);

  const handleLocaleChange = event => {
    const langKey = event.target.value;
    Storage.session.set('locale', langKey);
    props.onLocaleChange(langKey);
  };

  const renderDevRibbon = () =>
    props.isInProduction === false ? (
      <div className="ribbon dev">
        <a href="">
          <Translate contentKey={`global.ribbon.${props.ribbonEnv}`} />
        </a>
      </div>
    ) : null;

  useEffect(() => {
    setTotalPrix(props.panieramine)// setTotalPrix(totalPrix)
  }, [props.panieramine])// se base sur [props.panieramine]

  const toggleMenu = () => setMenuOpen(!menuOpen);

  const handleNav = () => {
    console.log("ok");
    props.reset();

  }
  const handleNav2 = () => {
    props.switchSideNav(props.switchNav)
  }
  /* jhipster-needle-add-element-to-menu - JHipster will add new menu items here */

  return (
    <div id="app-header">
      <LoadingBar className="loading-bar" />
      <Navbar expand="xs" fixed="top" className="jh-navbar">
        <NavbarToggler aria-label="Menu" onClick={toggleMenu} />
        <MenuI handelsidenav2={handleNav2} />
        <Brand handelsidenav={handleNav} />

        <ELBAZAR handelsidenav={handleNav} />

        <Grid container item sm={4} direction="row" spacing={1} >
          {props.listInformationsociete && props.listInformationsociete[0] ? (
            <div>

              <Grid item container xs={12}>
                <Grid>
                  <MarkunreadIcon style={{ color: 'red' }} />
                </Grid>
                <Grid style={{ color: 'red' }}>{props.listInformationsociete[0].email1}</Grid>
              </Grid>

              <Grid item container xs={12}>
                <Grid>
                  <PermPhoneMsgIcon style={{ color: 'red' }} />
                </Grid>

                <Grid style={{ color: 'red' }}>{props.listInformationsociete[0].tel1}<i>/</i> {props.listInformationsociete[0].tel2}                    </Grid>
              </Grid>
            </div>
          ) : null}
        </Grid>


        <Collapse isOpen={menuOpen} navbar>


          <Nav id="header-tabs" className="ml-auto" navbar>

            <CGUModalComment showModal={showModal} handleClose={handleClose} />
            <a style={{ color: 'red' }} onClick={HandleShow}>Comment ça marche</a>

            <Panier nbProduit={props.listProduitsPanier.length} total={props.panieramine} />



            <Home handelsidenav={handleNav} />
            <LocaleMenu currentLocale={props.currentLocale} onClick={handleLocaleChange} />
            <AccountMenu isAuthenticated={props.isAuthenticated} />
          </Nav>
        </Collapse>
      </Navbar>
    </div>
  );
};
const mapStateToProps = ({ categories, panier, produit, producthome }: IRootState) => ({
  listCategories: categories.listCategories,
  listProduitsPanier: panier.listProduitsPanier,
  switchNav: categories.showedSidNav,
  panieramine: panier.prixTotal,
  listSearchProduit: produit.entities,
  listInformationsociete: producthome.listInfosociete,

});

const mapDispatchToProps = {
  switchSideNav,
  getEntitieshome,
  reset,
  getinfosociete,


};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Header);
