import { useState } from "react";

export default function Login() {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    async function handleLogin(e: React.FormEvent) {
        e.preventDefault();

        const response = await fetch("http://localhost:8050/api/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ email, password }),
        });

        if (!response.ok) {
            alert("Invalid credentials");
            return;
        }

        const data = await response.json();

        // store JWT
        localStorage.setItem("token", data.token);

        // redirect after login
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

            <form className="container" onSubmit={handleLogin}>
                <h1 className="font-bold text-3xl m-2" style={{paddingTop: "12px"}}>Log in</h1>

                <label>Email:</label>
                <input
                    className="text-sm"
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />

                <br/><br/>

                <label>Password:</label>
                <input
                    className="text-sm"
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />

                <br /><br />

                <input className="text-lg" type="submit" value="Log In" />

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