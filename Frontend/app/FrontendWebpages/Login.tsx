import React, {use, useEffect, useState} from "react";
import {Alert, CloseButton, Spinner} from "@heroui/react";

export default function Login() {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [errorMessage, setErrorMessage] = useState(null as string | null);
    const [loading, setLoading] = useState(false);

    async function handleForm(e: React.SubmitEvent) {
        e.preventDefault()

        await handleLogin()
    }

    async function handleLogin() {
        setErrorMessage(null);
        setLoading(true);

        const response = await fetch("http://localhost:8050/api/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ email, password }),
        });

        setLoading(false);

        if (response.status === 502) {
            setErrorMessage("Email and password is not valid.")
            return
        }

        const data = await response.json();

        localStorage.setItem("token", data.token);

        window.location.href = "/";
    }

    return (
        <div className="centered">
            <a href="/" style={{ borderRadius: "200px" }}>
                <img
                    src="/images/navi/Navi%20Unique.png"
                    alt="Logo"
                    style={{ width: "100px", height: "100px" }}
                />
            </a>
            <br/>

            <form className="container" onSubmit={handleForm}>

                {errorMessage && (
                    <>
                        <br/>
                        <Alert className="dark rounded-4xl" style={{background: "var(--secondary-background-color)"}} status="danger">
                            <Alert.Indicator className="pr-0">
                                <img src="/images/assets/exclamationmark.circle.fill@4x.png" alt="Logo" style={{width: "20px", height: "20px"}}/>
                            </Alert.Indicator>
                            <Alert.Content>
                                <Alert.Title>
                                    <p className="font-bold" style={{marginTop: "2.2px", color: "rgb(225, 66, 69)"}}>
                                        Your Email &/or Password may be incorrect
                                    </p>
                                </Alert.Title>
                            </Alert.Content>
                            <CloseButton style={{background: "var(--tertiary-background-color)", marginTop: "2.2px"}} onClick={() => setErrorMessage(null)} />
                        </Alert>
                        <br/>
                    </>
                )}

                {!errorMessage && (
                    <>
                        <h1 className="font-bold text-3xl m-2" style={{paddingTop: "12px"}}>Sign in</h1>
                    </>
                )}

                <label>Email:</label>
                <input
                    id="email"
                    className="text-sm"
                    type="email"
                    value={email}
                    placeholder="Craig@Internmap.co"
                    onChange={(e) => setEmail(e.target.value)}
                    onInput={() => {setErrorMessage(null)}}
                    autoFocus={true}
                    onKeyDownCapture={(e) => {if (e.key === 'Enter') { if (password.length > 0) {return handleLogin()} else { document.getElementById("password")?.focus() }}}}
                    required
                />

                <br/><br/>

                <label>Password:</label>
                <input
                    id="password"
                    className="text-sm"
                    type="password"
                    value={password}
                    placeholder="Anything"
                    onChange={(e) => setPassword(e.target.value)}
                    onInput={() => {setErrorMessage(null)}}
                    onKeyDownCapture={(e) => {if (e.key === 'Enter') { if (email.length > 0) {return handleLogin()} else { document.getElementById("email")?.focus()} }}}
                    required
                />

                <br />

                { loading ? <Spinner size="lg" color="current" /> : <><br /> <input className="text-lg" type="submit" onKeyDownCapture={(e) => {if (e.key === 'Enter') {return handleLogin()}}} value="Log In" /></>}

                <br />

                <p style={{ fontSize: "14px" }}>
                    Need to sign up first?{" "}
                    <a href="/signup" style={{ color: "rgb(49, 131, 254)", fontWeight: 600 }}>
                        Sign up
                    </a>
                </p>
                <br/>
            </form>
        </div>
    );
}
