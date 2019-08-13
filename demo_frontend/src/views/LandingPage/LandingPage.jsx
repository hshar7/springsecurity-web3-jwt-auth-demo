import React from "react";
import axios from "axios";
import {base} from "../../constants";
import Web3 from "web3";

class LandingPage extends React.Component {

    state = {
        response: "",
        web3: null
    };

    componentDidMount = () => {
        this.setState({web3: new Web3(window.web3.currentProvider)});
    };

    signMessage = () => {
        console.log(this.state.web3.eth.accounts);
        this.state.web3.personal.sign(
            this.state.web3.sha3("hello world"), this.state.web3.eth.accounts[0],
            (error, result) => {
                if (!error) {
                    console.log(result);
                    axios
                        .post(`${base}/api/user`, {
                            publicAddress: this.state.web3.eth.accounts[0],
                            signature: result
                        })
                        .then(response => {
                            localStorage.setItem(
                                "publicAddress",
                                response.data.publicAddress
                            );
                            localStorage.setItem("userId", response.data.userId);
                            localStorage.setItem("jwtToken", response.data.accessToken);

                            // Configure axios
                            axios.defaults.headers.common = {
                                "Authorization": "Bearer " + localStorage.getItem("jwtToken")
                            };

                        })
                        .catch(error => {
                            console.error({error});
                        });
                } else {
                    console.error(error);
                }
            });
    };

    getMyDetails = () => {
        axios.get(`${base}/api/userDetails`).then(response => {
            this.setState({response: JSON.stringify(response.data, null, 2)});
        }).catch(error => {
            this.setState({response: JSON.stringify(error.response.data, null, 2)});
        })
    };

    render() {
        return (
            <div>
                <button onClick={() => this.signMessage()}>
                    Sign message and login
                </button>
                <button onClick={() => this.getMyDetails()}>
                    Get My Details
                </button>
                <p>
                    {this.state.response}
                </p>
            </div>
        );
    }
}

export default LandingPage;
