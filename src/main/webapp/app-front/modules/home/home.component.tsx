import React, { useState } from 'react';
import { makeStyles, createStyles, Theme, Box } from '@material-ui/core';
import { Link, RouteComponentProps } from 'react-router-dom';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';

import CardContent from '@material-ui/core/CardContent';

import IconButton from '@material-ui/core/IconButton';
import Typography from '@material-ui/core/Typography';
import { red, green, lime, lightGreen } from '@material-ui/core/colors';

import ShoppingCartIcon from '@material-ui/icons/ShoppingCart';
import { height } from '@fortawesome/free-solid-svg-icons/faAngleRight';
import EllipsisText from 'react-ellipsis-text';
import Rating from '@material-ui/lab/Rating';
import Badge from '@material-ui/core/Badge';
import { divide } from 'lodash';
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    root: {
      flexGrow: 1,
      width: '100%',
      backgroundColor: theme.palette.background.paper,
    },
    icone: {
      color: 'white',
      marginTop: '-5px',
    },
    media: {
      padding: '10%',
      width: '100%',
      height: '55%',
      margin: 'auto',
      cursor: 'pointer',
    },
    cardHeader: {
      backgroundColor: green['700'],
      color: 'white',
      height: '50px',
    },
    cardHeaderRed: {
      backgroundColor: red['600'],
      color: 'white',
      height: '50px',
    },
    badge: {
      position: 'relative',
      top: '100px',
      left: '-18%',
    },
    cardContent: {
      padding: '10px 10px 0px 10px',
    },

    cardContentItem: {
      fontWeight: 600,
      cursor: 'pointer',
    },
    cardContentItem2: {
      color: red[500],
      fontWeight: 700,
    },
    rating: {
      float: 'left',
      display: 'block',
      paddingTop: '0px',
    },
    expand: {
      transform: 'rotate(0deg)',
      marginLeft: 'auto',
      transition: theme.transitions.create('transform', {
        duration: theme.transitions.duration.shortest,
      }),
    },
    expandOpen: {
      transform: 'rotate(180deg)',
    },
    avatar: {
      backgroundColor: red[500],
    },
  })
);

export const ProductItem = props => {
  const [value, setValue] = React.useState(0);

  const handleChange = (event: React.ChangeEvent<{}>, newValue: number) => {
    setValue(newValue);
  };
  const classes = useStyles();
  const [expanded, setExpanded] = React.useState(false);

  const showDetail = () => {
    
  };
  const addToCart = () => {
    const itemPanier= props.listprodpanier.filter(item=>item.id===props.product.id);
    if((itemPanier[0] && itemPanier[0].qte<itemPanier[0].quantite_vente_max) || !itemPanier[0]) {
      props.addFunction(props.product,1);
        props.setopensnack(true);
        
    }else{
      props.setopensnackerror(true);
    }
  };
  const IsHorsStock = horsStock => {
    if (!horsStock) {
      return (
        <React.Fragment>
          <CardHeader
            className={classes.cardHeader}
            action={
              <IconButton onClick={addToCart} className={classes.icone} aria-label="settings">
                <ShoppingCartIcon />
              </IconButton>
            }
            titleTypographyProps={{ variant: 'subtitle1' }}
            title="Disponible"
          />
        </React.Fragment>
      );
    } else {
      return (
        <React.Fragment>
          <CardHeader className={classes.cardHeaderRed} titleTypographyProps={{ variant: 'subtitle1' }} title="Indisponible" />
        </React.Fragment>
      );
    }
  };
  const IsPromo = product => {
    const currentDate = new Date();
    const startDate = new Date(product.debutpromo);
    const endDate = new Date(product.finpromo);
    currentDate.setHours(0,0,0,0)
      startDate.setHours(0,0,0,0)
      endDate.setHours(0,0,0,0)
    const isInPromo = startDate.getTime() <= currentDate.getTime() && currentDate.getTime() <= endDate.getTime();
    if (isInPromo) {
      return (
        <React.Fragment>
          <Typography
            variant="body2"
            className={classes.cardContentItem2}
            style={{ textDecoration: 'line-through', float: 'left', color: '#999' }}
            component="p"
          >
            {props.product.prixTTC + ' Dt'}
          </Typography>
          <Typography
            variant="body2"
            className={classes.cardContentItem2}
            style={{ float: 'left', marginLeft: '12px' }}
            color="textSecondary"
            component="p"
          >
            {(parseInt(props.product.prixTTC, 10) * parseInt(props.product.remise, 10)) / 100 + ' Dt'}
          </Typography>
        </React.Fragment>
      );
    } else {
      return (
        <React.Fragment>
          <Typography variant="body2" className={classes.cardContentItem2} color="textSecondary" component="p">
            {props.product.prixTTC + ' Dt'}
          </Typography>
        </React.Fragment>
      );
    }
  };
  const IsPromoBadge = product => {
    const currentDate = new Date();
    const startDate = new Date(product.debutpromo);
    const endDate = new Date(product.finpromo);
    currentDate.setHours(0,0,0,0)
    startDate.setHours(0,0,0,0)
    endDate.setHours(0,0,0,0)
    const isInPromo = startDate.getTime() <= currentDate.getTime() && currentDate.getTime() <= endDate.getTime();
    if (isInPromo) {
      return <Badge badgeContent={'promo'} color="error" className={classes.badge}></Badge>;
    } else {
      return <div></div>;
    }
  };
  return (
    <Card className="cardItem">
      {IsHorsStock(props.product.horsstock)}

      <Link to={`/product/${props.product.id}`}>
        <img
          className={classes.media}
          onClick={showDetail}
          src={'data:image/bmp;base64,' + props.product.image}
          title={props.product.nom}
        />

        {IsPromoBadge(props.product)}
      </Link>
      <CardContent className={classes.cardContent}>
        <Typography onClick={showDetail} className={classes.cardContentItem} variant="body2" color="textSecondary" component="p">
          <EllipsisText text={props.product.nom} length={25} />
        </Typography>
      </CardContent>
      <CardContent className={classes.cardContent} style={{ height: '10%' }}>
        {IsPromo(props.product)}
      </CardContent>
      <CardContent className={classes.rating}>
        <Rating name="pristine" value={3} />
      </CardContent>
    </Card>
  );
};
