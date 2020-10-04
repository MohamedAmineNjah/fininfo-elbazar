import React, { useState, useEffect } from 'react';

import { connect } from 'react-redux';

import { IRootState } from '../../../shared/reducers';
import { getSession } from '../../../shared/reducers/authentication';
import { getEntity, updateEntity } from '../../../entities/client/client.reducer';
import { saveAccountSettings, reset } from './profil-main.reducer';
import SwipeableViews from 'react-swipeable-views';
import { useTheme } from '@material-ui/core/styles';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import { Paper } from '@material-ui/core';
import { Route, RouteComponentProps } from 'react-router-dom';
import { AdresseProfilFront } from '../profil/adresse-tab';
import { CommandeProfilFront } from '../profil/commande-tab';
import { DonneesPerso } from '../profil/donnees-perso-tab';
import AccountCircleIcon from '@material-ui/icons/AccountCircle';
import RoomIcon from '@material-ui/icons/Room';
import ShoppingBasketIcon from '@material-ui/icons/ShoppingBasket';
import TabPanel from './tab-panel';
import { useStyles, a11yProps } from './profil-main-services';

export interface ISettingsPageProps extends StateProps, DispatchProps {}

export const SettingsPage = (props: ISettingsPageProps) => {
  const classes = useStyles();
  const theme = useTheme();
  
  const [refresh, setRefresh] = useState(true);
  const [value, setValue] = useState(0);

  useEffect(() => {}, [props.account]);

  useEffect(() => {}, [props.cliententity]);

  const handleChange = (event: React.ChangeEvent<{}>, newValue: number) => {
    setValue(newValue);
  };
  const handleChangeRefresh=() => {
    setRefresh(!refresh);
  };
  const handleChangeIndex = (index: number) => {
    setValue(index);
  };

  return (
    <React.Fragment>
      <Paper className={classes.root}>
        <Tabs
          value={value}
          onChange={handleChange}
          indicatorColor="secondary"
          textColor="secondary"
          variant="fullWidth"
          aria-label="full width tabs example"
          centered
        >
          <Tab icon={<AccountCircleIcon />} label="données personnelles" {...a11yProps(0)} />
          <Tab icon={<RoomIcon />} label="Mes Adresses" {...a11yProps(1)} />
          <Tab icon={<ShoppingBasketIcon />} label="Mes Commandes" {...a11yProps(2)} />
        </Tabs>
      </Paper>
      <SwipeableViews
        enableMouseEvents
        axis={theme.direction === 'rtl' ? 'x-reverse' : 'x'}
        index={value}
        onChangeIndex={handleChangeIndex}
      >
        {props.account.activated ? (
          <TabPanel value={value} index={0} >
            <DonneesPerso style={{padding:"20px"}} key={refresh.toString()} setrefresh={handleChangeRefresh} accountdetail={props.account} />
          </TabPanel>
        ) : null}
        {props.account.activated ? (
          <TabPanel value={value} index={1} >
            <AdresseProfilFront style={{padding:"20px"}} key={refresh.toString()} setrefresh={handleChangeRefresh} idadresse={props.adresseentity} accountdetailadresse={props.account} ispanier={false} />
          </TabPanel>
        ) : null}

        {/* <TabPanel value={value} index={1} dir={theme.direction}>
          <Route component={AdresseProfilFront} />
        </TabPanel> */}
        {/* <TabPanel value={value} index={2} dir={theme.direction}>
          <Route component={CommandeProfilFront} />
        </TabPanel> */}
        {props.account.activated ? (
          <TabPanel value={value} index={2} >
            <CommandeProfilFront style={{padding:"20px"}}  accountdetailcommandes={props.account} />
          </TabPanel>
        ) : null}
      </SwipeableViews>
    </React.Fragment>
  );
};

const mapStateToProps = ({ authentication, client, adresse }: IRootState) => ({
  account: authentication.account,
  cliententity: client.entity,
  adresseentity: adresse.entity,
});

const mapDispatchToProps = { getSession, getEntity, updateEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SettingsPage);
