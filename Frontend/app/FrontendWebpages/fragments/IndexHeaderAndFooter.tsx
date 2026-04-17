import {useState, useEffect} from "react";
import {notification} from "~/FrontendWebpages/fragments/Notification";

export function IndexHeader() {

    const [isLoggedIn, setIsLoggedIn] = useState(false);

    useEffect(() => {
        const key = localStorage.getItem("token");
        if (key != null && key.trim() !== "") {
            setIsLoggedIn(true);
        }

        notification()

    }, []);

    return (
        <header className="header">
            <section className="section wide" onClick={() => location.href = '/'}>
                {/*<img src="/Images/Mono/Straight%20Hero.png" alt="Logo" style="height: 55px; width: 55px;">*/}
                <img className="logo" src="/images/navi/Navi%20Unique.png" alt="Logo"/>
                <h1 className="text-3xl font-bold">InternMap</h1>
            </section>

            {!isLoggedIn ? <section className="section">
                <button className="button-secondary" onClick={() => location.href = '/login'}>Sign in</button>
                <button className="button-prominant" onClick={() => location.href = '/signup'}>Sign up</button>
            </section> : <section className="section wide">
                <form method="POST" action={'/logout'}>
                    <button className="button-prominant" type="submit" value="Log out">Log out</button>
                </form>

                <button className="for-icon" onClick={() => location.href = '/profile'}>
                    <img className="icon clickable" src="/images/person_fill.png" alt="Profile"
                         style={{marginTop: "2px", marginLeft: "1px"}}/>
                </button>
            </section>}

            <div id="notificationBox" style={{position: "fixed", top: "20px", right: "20px", background: "#333", color: "white", padding: "10px 20px", borderRadius: "30px", display: "none", zIndex: 1000}}></div>
        </header>
    )
}

export function IndexFooter() {
    return (
        <footer>
            <div className="footer-container">
                <section className="section">
                    <img src="/images/navi/Navi%20Straight.png" alt="Logo" height={38} width={38} style={{marginRight: "10px"}}/>
                    <h2 className="text-xl font-bold">InternMap</h2>
                </section>
                <h4 className="font-semibold" style={{cursor: "pointer"}}><a href={'/'}>Home</a></h4>
            </div>
        </footer>
    )
}