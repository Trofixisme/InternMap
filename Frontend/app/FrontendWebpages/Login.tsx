export default function Login() {
    return (
    <div className="centered">
        <a href={"/"} style={{borderRadius: "200px"}} inert><img src="/images/navi/Navi%20Unique.png" alt="Logo"
                                                                   style={{width: "100px", height: "100px"}}/></a>
        <br/>

        <form className="container" method="post">
            <div /*th:if="${errorMessage}"*/
                 style={{color: "red", border: "0 solid red", padding: "2px", marginBottom: "5px", fontWeight: 550}}>
                <p /*th:text="${errorMessage}"*/></p>
            </div>

            <h1 className="font-bold text-3xl m-2">Log in</h1>
            <label htmlFor="email">Email:</label>
            <input className="text-sm" type="email" id="email" name="email" placeholder="example@gmail.com" required
                   autoComplete="email"/><br/><br/>
            <label htmlFor="password">Password:</label>
            <input className="text-sm" type="password" id="password" name="password" placeholder="Enter your password" required/><br/><br/>
            <input className="text-lg" type="submit" value="Log In"/>
            <br/>
            <p style={{fontSize: "14px", fontWeight: 400}}>Need to sign up first? <a
                style={{color: "rgb(49, 131, 254)", fontWeight: 600, borderRadius: "200px"}} href={"/signup"}>Sign
                up</a></p>
            <br/>
        </form>
    </div>
    )
}