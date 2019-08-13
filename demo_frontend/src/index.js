import React from "react";
import ReactDOM from "react-dom";
import {createBrowserHistory} from "history";
import {Router, Route, Switch} from "react-router-dom";
import axios from "axios";
import "assets/scss/material-kit-react.scss?v=1.4.0";
// pages for this product
import LandingPage from "views/LandingPage/LandingPage.jsx";

const hist = createBrowserHistory();

// Configure axios
axios.defaults.headers.common = {
    "Authorization": "Bearer " + localStorage.getItem("jwtToken")
};

ReactDOM.render(
    <Router history={hist}>
        <Switch>
            <Route path="/" component={LandingPage} history={hist}/>
        </Switch>
    </Router>,
    document.getElementById("root")
);
