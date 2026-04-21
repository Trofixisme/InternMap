import {useState, useEffect} from "react";
import {
    AlertDialog,
    Button,
    Description,
    Dropdown,
    Kbd,
    Label,
    Modal,
    Separator,
    Toast,
    useOverlayState
} from "@heroui/react";
import {notification} from "~/FrontendWebpages/fragments/Notification";
import {useLocation} from "react-router";

function TrashBin(props: { className: string }) {
    return null;
}

function Pencil(props: { className: string }) {
    return null;
}

function SquarePlus(props: { className: string }) {
    return null;
}

function EllipsisVertical(props: { className: string }) {
    return null;
}

export function IndexHeader() {

    const state = useOverlayState({
        defaultOpen: false,
        onOpenChange: () => {
        }
    })

    if ((localStorage.getItem("showOnboarding") == null || localStorage.getItem("showOnboarding") == "true") && !state.isOpen) {
        state.open()
    }

    function closeOnboarding() {
        localStorage.setItem("showOnboarding", "false");
        state.close()
    }

    const [isLoggedIn, setIsLoggedIn] = useState(false);

    function logout() {
        location.href = "/logout";
    }

    useEffect(() => {
        const key = localStorage.getItem("token");
        if (key != null && key.trim() !== "") {
            setIsLoggedIn(true);
        }

    }, []);

    notification()

    return (
        <header className="header">

            <Modal isOpen={state.isOpen}>
                <Modal.Backdrop className="dark" variant="blur" isKeyboardDismissDisabled={false} isDismissable={true}>
                    <Modal.Container>
                        <Modal.Dialog className="sm:max-w-90 rounded-4xl">
                            <Modal.CloseTrigger onClick={() => closeOnboarding()} />
                            <Modal.Header>
                                <img src="/images/navi/Navi%20Beta.png" alt="Logo" style={{height: "60px", width: "60px"}}/>
                                <Modal.Heading>Welcome to Internmap!</Modal.Heading>
                            </Modal.Header>
                            <Modal.Body>
                                <p>
                                    Internmap is still in development. It's
                                    our fourth semester's final project made by four inexperienced developers
                                    who also happen to be friends.
                                </p>
                            </Modal.Body>
                            <Modal.Footer>
                                <Button className="w-full" onClick={() => closeOnboarding() } slot="close">
                                    Continue
                                </Button>
                            </Modal.Footer>
                        </Modal.Dialog>
                    </Modal.Container>
                </Modal.Backdrop>
            </Modal>

            <section className="section wide" onClick={() => location.href = '/'}>
                <img className="logo" src="/images/navi/Navi%20Unique.png" alt="Logo"/>
                <h1 className="text-3xl font-bold">InternMap</h1>
            </section>

            {!isLoggedIn ? <section className="section wide">
                <button className="button-secondary" onClick={() => location.href = '/login'}>Sign in</button>
                <button className="button-prominant" onClick={() => location.href = '/signup'}>Sign up</button>
            </section> : <section className="section wide">

                <Dropdown>
                    <Button isIconOnly aria-label="Menu" variant="ghost">
                        <img src="/images/assets/ellipsis@4x.png" alt="ellipsis" style={{height: "5px"}}/>
                    </Button>
                    <Dropdown.Popover>
                        <Dropdown.Menu onAction={(key) => console.log(`Selected: ${key}`)}>
                            <Dropdown.Section>
                                <Dropdown.Item id="new-file" textValue="New file">
                                    <div className="flex h-8 items-start justify-center pt-px">
                                        <SquarePlus className="size-4 shrink-0 text-muted" />
                                    </div>
                                    <div className="flex flex-col">
                                        <Label>New file</Label>
                                        <Description>Create a new file</Description>
                                    </div>
                                    <Kbd className="ms-auto" slot="keyboard" variant="light">
                                        <Kbd.Abbr keyValue="command" />
                                        <Kbd.Content>N</Kbd.Content>
                                    </Kbd>
                                </Dropdown.Item>
                                <Dropdown.Item id="edit-file" textValue="Edit file">
                                    <div className="flex h-8 items-start justify-center pt-px">
                                        <Pencil className="size-4 shrink-0 text-muted" />
                                    </div>
                                    <div className="flex flex-col">
                                        <Label>Edit file</Label>
                                        <Description>Make changes</Description>
                                    </div>
                                    <Kbd className="ms-auto" slot="keyboard" variant="light">
                                        <Kbd.Abbr keyValue="command" />
                                        <Kbd.Content>E</Kbd.Content>
                                    </Kbd>
                                </Dropdown.Item>
                            </Dropdown.Section>
                            <Separator />
                            <Dropdown.Section>
                                <Dropdown.Item id="delete-file" textValue="Delete file" variant="danger">
                                    <div className="flex h-8 items-start justify-center pt-px">
                                        <TrashBin className="size-4 shrink-0 text-danger" />
                                    </div>
                                    <div className="">
                                        <Label>Sign out</Label>
                                    </div>
                                    <Kbd className="ms-auto" slot="keyboard" variant="light">
                                        <Kbd.Abbr keyValue="command" />
                                        <Kbd.Abbr keyValue="shift" />
                                        <Kbd.Content>D</Kbd.Content>
                                    </Kbd>
                                </Dropdown.Item>
                            </Dropdown.Section>
                        </Dropdown.Menu>
                    </Dropdown.Popover>
                </Dropdown>

                <AlertDialog>
                    <Button className="font-semibold">Sign out</Button>
                    <AlertDialog.Backdrop variant="blur" isKeyboardDismissDisabled={false} isDismissable={true}>
                        <AlertDialog.Container>
                            <AlertDialog.Dialog className="sm:max-w-100 rounded-4xl">
                                <AlertDialog.Header>
                                    <img className="w-8" src="/images/assets/exclamationmark.circle.fill@4x.png" alt="Warn"/>
                                    <AlertDialog.Heading>Sign out?</AlertDialog.Heading>
                                </AlertDialog.Header>
                                <AlertDialog.Body>
                                    <p>Are you sure you want to sign out? You will not be able to track your progression across roadmaps or apply to jobs... </p>
                                </AlertDialog.Body>
                                <AlertDialog.Footer>
                                    <Button slot="close" variant="tertiary">
                                        Cancel
                                    </Button>

                                    <Button slot="close" onClick={() => logout()} variant="danger">

                                        {/*  Works but makes clicking Enter after dismissing the dialog to log you out either way */}
                                        {/*  I could remove the event listener upon dismissing the dialog, but I'm not very sure about the efficiency of doing so  */}

                                        {/*{document.addEventListener("keydown", e => {*/}
                                        {/*    if (e.key == 'Enter') {*/}
                                        {/*        logout()*/}
                                        {/*    }*/}
                                        {/*} , false)}*/}

                                        Sign out
                                    </Button>
                                </AlertDialog.Footer>
                            </AlertDialog.Dialog>
                        </AlertDialog.Container>
                    </AlertDialog.Backdrop>
                </AlertDialog>

                {!useLocation().pathname.includes("/profile") && (

                <button className="for-icon" onClick={() => location.href = '/profile'}>
                    <img className="icon clickable" src="/images/person_fill.png" alt="Profile"
                         style={{marginTop: "2px", marginLeft: "1px"}}/>
                </button>)}
            </section>}

            <Toast.Provider placement="top end"/>

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