import "~/CSS/jobPosting.css"
import "~/CSS/InternMapHomepage.css";
import {IndexFooter, IndexHeader} from "./fragments/IndexHeaderAndFooter";
import {Checkbox, Tabs, Toast} from "@heroui/react";
import {notification} from "~/FrontendWebpages/fragments/Notification";
import { Table } from '@heroui/react';
import {useState} from "react";
import {Button , Alert} from "@heroui/react";
import type {Key} from "node:readline";
import {useFetcher} from "react-router";
import {AlertDialog} from "@heroui/react";


export default function Dashboard({users , roadmaps}: {users : User[], roadmaps : Roadmap[]}) {
    const fetcher = useFetcher()
    const [selectedKeys, setSelectedKeys] = useState<"all" | Set<Key>>(new Set());

    const [showAdminError, setShowAdminError] = useState(false);
    return (

        <>
            <IndexHeader/>

            {showAdminError && (
                <Alert status="danger">
                    <Alert.Indicator />
                    <Alert.Content>
                        <Alert.Title>Cannot delete admin user</Alert.Title>
                        <Alert.Description>
                            You selected at least one ADMIN user. This action is blocked.
                        </Alert.Description>
                    </Alert.Content>
                </Alert>
            )}

            <div className="wrapper">

                <div id="bb1" >

                    <Tabs className="max-w">
                        <Tabs.ListContainer>
                            <Tabs.List aria-label="View Selector">

                                <Tabs.Tab id="Roadmap">
                                    Users
                                    <Tabs.Indicator/>
                                </Tabs.Tab>

                                <Tabs.Tab id="JobPostings">
                                    RoadMaps
                                    <Tabs.Indicator/>
                                </Tabs.Tab>
                            </Tabs.List>
                        </Tabs.ListContainer>

                        <Tabs.Panel id="Roadmap">

                            <div >


                                {users.length == 0 ?
                                    <a> No users to show </a> :
                                    <div>

                                        <div  className="flex flex-col gap-3">
                                            <Table >
                                                <Table.ScrollContainer  style={{ maxHeight: "600px", overflow: "auto"  }}>
                                                    <Table.Content

                                                        aria-label="Table with selection"
                                                        className="min-w-[600px]"
                                                        selectedKeys={selectedKeys}
                                                        selectionMode="multiple"
                                                        onSelectionChange={setSelectedKeys}>
                                                        <Table.Header>
                                                            <Table.Column className="pr-0">
                                                                <Checkbox aria-label="Select all" slot="selection">
                                                                    <Checkbox.Control>
                                                                        <Checkbox.Indicator />
                                                                    </Checkbox.Control>
                                                                </Checkbox>
                                                            </Table.Column>
                                                            <Table.Column isRowHeader>FName</Table.Column>
                                                            <Table.Column isRowHeader>LName</Table.Column>

                                                            <Table.Column>Role</Table.Column>
                                                            <Table.Column>ID</Table.Column>
                                                            <Table.Column>Email</Table.Column>
                                                        </Table.Header>
                                                        <Table.Body>
                                                            {users.map((user) => (
                                                                <Table.Row key={user.id} id={user.email}>
                                                                    <Table.Cell className="pr-0">
                                                                        <Checkbox
                                                                            aria-label={`Select ${user.name}`}
                                                                            slot="selection"
                                                                            variant="secondary"
                                                                        >
                                                                            <Checkbox.Control>
                                                                                <Checkbox.Indicator />
                                                                            </Checkbox.Control>
                                                                        </Checkbox>
                                                                    </Table.Cell>
                                                                    <Table.Cell>{user.fname}</Table.Cell>
                                                                    <Table.Cell>{user.lname}</Table.Cell>

                                                                    <Table.Cell>{user.role}</Table.Cell>
                                                                    <Table.Cell>{user.id}</Table.Cell>
                                                                    <Table.Cell>{user.email}</Table.Cell>
                                                                </Table.Row>
                                                            ))}
                                                        </Table.Body>
                                                    </Table.Content>
                                                </Table.ScrollContainer>
                                            </Table>

                                        </div>
                                    </div>
                                }




                            </div>
                            <p className="text-sm text-muted">
                                Users Selected:{" "}
                                <span className="font-medium">
                                                     {selectedKeys === "all"  ? "All"  : selectedKeys.size > 0  ? selectedKeys.size : "None"}
                                                </span>

                            </p>
                            <div>

                                <AlertDialog>
                                    <Button variant="danger">Smite</Button>
                                    <AlertDialog.Backdrop>
                                        <AlertDialog.Container>
                                            <AlertDialog.Dialog className="sm:max-w-[400px]">
                                                <AlertDialog.CloseTrigger />
                                                <AlertDialog.Header>
                                                    <AlertDialog.Icon status="danger" />
                                                    <AlertDialog.Heading>Delete project permanently?</AlertDialog.Heading>
                                                </AlertDialog.Header>
                                                <AlertDialog.Body>
                                                    <p>
                                                        This will permanently delete <strong>This user</strong> and all of his
                                                        data. This action cannot be undone.
                                                    </p>
                                                </AlertDialog.Body>
                                                <AlertDialog.Footer>
                                                    <Button slot="close" variant="tertiary">
                                                        Cancel
                                                    </Button>

                                                    <Button slot="close" variant="danger"   onPress={() => {

                                                        const selectedUsers =
                                                            selectedKeys === "all" ? users : users.filter(u => (selectedKeys as Set<string>).has(u.email));
                                                               if(selectedUsers.some (u => u.role === "ADMIN")) {
                                                                   setShowAdminError(true);
                                                                   setTimeout(() => {
                                                                       setShowAdminError(false);
                                                                   }, 3000);
                                                                   return;
                                                               }

                                                        const selectedEmails = selectedKeys === "all"
                                                            ? users.map(u => <u className="email"></u>)
                                                            : Array.from(selectedKeys as Set<string>);

                                                        const formData = new FormData();
                                                        selectedEmails.forEach(email => formData.append("emails", email));
                                                        fetcher.submit(formData, { method: "POST" });

                                                        setSelectedKeys(new Set())
                                                    }}>
                                                        Smite!
                                                    </Button>
                                                </AlertDialog.Footer>
                                            </AlertDialog.Dialog>
                                        </AlertDialog.Container>
                                    </AlertDialog.Backdrop>
                                </AlertDialog>

                                <button>

                                </button>
                            </div>

                        </Tabs.Panel>

                        <Tabs.Panel id="JobPostings">

                            <div >


                                {users.length == 0 ?
                                    <a> No users to show </a> :
                                    <div>

                                        <div  className="flex flex-col gap-3">
                                            <Table >
                                                <Table.ScrollContainer  style={{ maxHeight: "600px", overflow: "auto"}}>
                                                    <Table.Content

                                                        aria-label="Table with selection"
                                                        className="min-w-[600px]"
                                                        selectedKeys={selectedKeys}
                                                        selectionMode="multiple"
                                                        onSelectionChange={setSelectedKeys}>
                                                        <Table.Header>
                                                            <Table.Column className="pr-0">
                                                                <Checkbox aria-label="Select all" slot="selection">
                                                                    <Checkbox.Control>
                                                                        <Checkbox.Indicator />
                                                                    </Checkbox.Control>
                                                                </Checkbox>
                                                            </Table.Column>
                                                            <Table.Column isRowHeader>FName</Table.Column>
                                                            <Table.Column isRowHeader>ID</Table.Column>


                                                        </Table.Header>
                                                        <Table.Body>
                                                            {roadmaps.map((roadmaps) => (
                                                                <Table.Row key={roadmaps.name} id={roadmaps.id}>
                                                                    <Table.Cell className="pr-0">
                                                                        <Checkbox
                                                                            aria-label={`Select ${roadmaps.name}`}
                                                                            slot="selection"
                                                                            variant="secondary"
                                                                        >
                                                                            <Checkbox.Control>
                                                                                <Checkbox.Indicator />
                                                                            </Checkbox.Control>
                                                                        </Checkbox>
                                                                    </Table.Cell>
                                                                    <Table.Cell>{roadmaps.name}</Table.Cell>
                                                                    <Table.Cell>{roadmaps.id}</Table.Cell>

                                                                </Table.Row>
                                                            ))}
                                                        </Table.Body>
                                                    </Table.Content>
                                                </Table.ScrollContainer>
                                            </Table>

                                        </div>
                                    </div>
                                }




                            </div>
                            <p className="text-sm text-muted">
                                Users Selected:{" "}
                                <span className="font-medium">
                                                     {selectedKeys === "all"  ? "All"  : selectedKeys.size > 0  ? selectedKeys.size : "None"}
                                                </span>

                            </p>
                            <div>

                                <AlertDialog>
                                    <Button variant="danger">Smite</Button>
                                    <AlertDialog.Backdrop>
                                        <AlertDialog.Container>
                                            <AlertDialog.Dialog className="sm:max-w-[400px]">
                                                <AlertDialog.CloseTrigger />
                                                <AlertDialog.Header>
                                                    <AlertDialog.Icon status="danger" />
                                                    <AlertDialog.Heading>Delete project permanently?</AlertDialog.Heading>
                                                </AlertDialog.Header>
                                                <AlertDialog.Body>
                                                    <p>
                                                        This will permanently delete <strong>This user</strong> and all of his
                                                        data. This action cannot be undone.
                                                    </p>
                                                </AlertDialog.Body>
                                                <AlertDialog.Footer>
                                                    <Button slot="close" variant="tertiary">
                                                        Cancel
                                                    </Button>

                                                    <Button slot="close" variant="danger"   onPress={() => {

                                                        const selectedUsers =
                                                            selectedKeys === "all" ? users : users.filter(u => (selectedKeys as Set<string>).has(u.email));
                                                        if(selectedUsers.some (u => u.role === "ADMIN")) {
                                                            setShowAdminError(true);
                                                            setTimeout(() => {
                                                                setShowAdminError(false);
                                                            }, 3000);
                                                            return;
                                                        }

                                                        const selectedEmails = selectedKeys === "all"
                                                            ? users.map(u => <u className="email"></u>)
                                                            : Array.from(selectedKeys as Set<string>);

                                                        const formData = new FormData();
                                                        selectedEmails.forEach(email => formData.append("emails", email));
                                                        fetcher.submit(formData, { method: "POST" });

                                                        setSelectedKeys(new Set())
                                                    }}>
                                                        Smite!
                                                    </Button>
                                                </AlertDialog.Footer>
                                            </AlertDialog.Dialog>
                                        </AlertDialog.Container>
                                    </AlertDialog.Backdrop>
                                </AlertDialog>

                                <button>

                                </button>
                            </div>

                        </Tabs.Panel>
                    </Tabs>
                </div>
            </div>

            <IndexFooter/>
        </>
    );
}