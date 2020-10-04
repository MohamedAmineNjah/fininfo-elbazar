import { makeStyles, Theme, createStyles, createMuiTheme, ThemeProvider } from '@material-ui/core/styles';
import { green } from '@material-ui/core/colors';
import { Snackbar, Button } from '@material-ui/core';
import { Alert } from '@material-ui/lab';
import React, { useState, useEffect } from 'react';

export const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    modal: {
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center',
      outline: 'none',
    },
    root: {
      flexGrow: 1,
      display: 'flex',
      '& > * + *': {
        marginLeft: theme.spacing(2),
      },
    },
    heading: {
      fontSize: theme.typography.pxToRem(15),
      fontWeight: theme.typography.fontWeightRegular,
    },
    paper: {
      color: theme.palette.text.secondary,
      backgroundColor: theme.palette.background.paper,
      border: '2px solid #000',
      boxShadow: theme.shadows[5],
      padding: theme.spacing(2, 4, 3),
      textAlign: 'center',
    },
    icon: {
      color: '#E91F2B',
      fontSize: '2rem',
    },
    date: {
      width: '100%', // Fix IE 11 issue.
    },
    form: {
      width: '100%', // Fix IE 11 issue.
      marginTop: theme.spacing(3),
    },
    button: {
      // color: '#E91F2B',
      margin: theme.spacing(1),
    },
    icone: {
      color: 'green',
    },
    warning: {
      color: 'red',
    },
  })
);

export const theme = createMuiTheme({
  palette: {
    primary: green,
  },
});

export const SnackBarCustom = ({ isSnackOpen, handleClose, textMessage }) => {
  return (
    <div>
      <Snackbar
        anchorOrigin={{
          vertical: 'top',
          horizontal: 'center',
        }}
        open={isSnackOpen}
        autoHideDuration={6000}
        onClose={handleClose}
        action={
          <React.Fragment>
            <Button color="secondary" size="small" onClick={handleClose}>
              UNDO
            </Button>
          </React.Fragment>
        }
      >
        <Alert severity="success">{textMessage}</Alert>
      </Snackbar>
    </div>
  );
};
