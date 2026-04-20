import {IndexHeader} from "~/FrontendWebpages/fragments/IndexHeaderAndFooter";
import {Button, Chip, Table} from "@heroui/react";
import {ColumnResizer} from "react-aria-components";

export default function Profile({userDetails}: { userDetails: User}) {

    return (
        <>
            <IndexHeader/>

            <div className="pl-17 pt-8">
                <div className="flex items-center gap-4 flex-row">
                    <img src="/images/navi/Navi%20Beta.png"
                         style={{display: "flex", width: "100px", height: "100px", borderRadius: "100%"}} alt="Unstable Logo"/>
                    <div style={{gap: "7px", display: "flex", flexDirection: "column"}}>
                        <section>
                            <p className="auto-capitalise text-3xl font-bold">{userDetails.fname + " " + userDetails.lname}</p>
                            <p>{userDetails.email}</p>
                        </section>
                        <div className="flex items-center gap-4 flex-row">
                            <Chip style={{gap: "4px"}} size="lg" >
                                <img src="/images/assets/calendar@4x.png" alt="calendar"
                                     style={{width: "17px", filter: "invert(0.8)"}}/>
                                <Chip.Label>{userDetails.dateCreated.toString().substring(0, 4)}</Chip.Label>
                            </Chip>
                            <Chip style={{gap: "4px"}} size="lg">
                                <img src="/images/assets/person.fill@4x.png" alt="person"
                                     style={{width: "15px", filter: "invert(0.8)"}}/>
                                <Chip.Label>{userDetails.role.charAt(0) + userDetails.role.toLowerCase().substring(1, userDetails.role.length)}</Chip.Label>
                            </Chip>
                            {userDetails.role == "RECRUITER" && (
                            <Chip style={{gap: "4px"}} size="lg">
                              <img src="/images/assets/suitcase.fill@4x.png" alt="suitcase"
                                   style={{width: "15px", filter: "invert(0.8)"}}/>
                                <Chip.Label className="auto-capitalise">{(userDetails as Recruiter).title}</Chip.Label>
                            </Chip>
                            )}
                        </div>
                    </div>
                </div>
            </div>

            {/*<div className="grid grid-cols-2">*/}
                {/*<div className="">*/}
                {/*    <Tabs className="" orientation="vertical">*/}
                {/*        <Tabs.ListContainer>*/}
                {/*            <Tabs.List aria-label="Options">*/}
                {/*                <Tabs.Tab*/}
                {/*                    href="/docs/react/getting-started"*/}
                {/*                    id="getting-started"*/}
                {/*                    */}
                {/*                >*/}
                {/*                    Getting Started*/}
                {/*                    <Tabs.Indicator />*/}
                {/*                </Tabs.Tab>*/}
                {/*                <Tabs.Tab*/}
                {/*                    href="/docs/react/components"*/}
                {/*                    id="components"*/}
                {/*                    */}
                {/*                >*/}
                {/*                    Components*/}
                {/*                    <Tabs.Indicator />*/}
                {/*                </Tabs.Tab>*/}
                {/*                <Tabs.Tab*/}
                {/*                    href="/docs/react/releases"*/}
                {/*                    id="releases"*/}
                {/*                >*/}
                {/*                    Releases*/}
                {/*                    <Tabs.Indicator />*/}
                {/*                </Tabs.Tab>*/}
                {/*            </Tabs.List>*/}
                {/*        </Tabs.ListContainer>*/}
                {/*    </Tabs>*/}
                {/*</div>*/}

                <div>

                    <br/><br/>

                    {/*// <!-- Student Fields -->*/}
                    {userDetails.role == "STUDENT" && (
                        <>
                            <h4 className="text-3xl font-bold container-label">About</h4>

                            <div className="container-padded">
                                <div>
                                    <label className="label-small">Major</label>
                                    <p className="auto-capitalise">{(userDetails as Student).studentMajor}</p>
                                </div>

                                <div className="mb-3">
                                    <label className="label-small">Year</label>
                                    <p className="auto-capitalise">{(userDetails as Student).graduatingYear}</p>
                                </div>

                                <div className="mb-3">
                                    <label className="label-small">University</label>
                                    <p className="auto-capitalise">{(userDetails as Student).uniName}</p>
                                </div>
                            </div>

                            <br />
                            <br />

                            {/*// <!-- CV Section -->*/}
                            <div style={{display: "flex", flexDirection: "row", gap: "10px", alignItems: "center"}}>
                            <h4 className="text-3xl font-bold container-label">Circulmn Vitae</h4>
                                {(userDetails as Student).cv ? (<Button style={{width: "32px", height: "32px", background: "var(--secondary-background-color)"}} className="dark" isIconOnly>
                                    <img src="/images/assets/pencil@4x.png" style={{width: "16px", filter: "invert(0.3)"}} alt="pencil"/>
                                </Button>) : (<Button style={{width: "32px", height: "32px", background: "var(--secondary-background-color)"}} className="dark" isIconOnly>
                                    <img src="/images/assets/plus@4x.png" style={{width: "16px", filter: "invert(0.3)"}} alt="pencil"/>
                                </Button>)}

                            </div>

                            <div className="container-padded">
                            {(userDetails as Student).cv ? (
                                <>
                                        <div>
                                            <label className="label-small">Professional Summary</label>
                                            <p className="auto-capitalise">{(userDetails as Student).cv.description}</p>
                                        </div>

                                        <div>
                                            <label className="label-small">Past Experiences</label>
                                            <p style={{whiteSpace: "pre-wrap"}}>{(userDetails as Student).cv.pastExperiences}</p>
                                        </div>

                                        <div>
                                            <label className="label-small">Projects</label>
                                            <p style={{whiteSpace: "pre-wrap"}}>{(userDetails as Student).cv.projects}</p>
                                        </div>
                                </>
                            ) : (
                                <p className="text-muted">You don't have a CV</p>
                            )}
                            </div>
                        </>
                    )}

                    {/*// <!-- Recruiter Fields -->*/}
                    {userDetails.role == "RECRUITER" && (
                        <>
                            <h4 className="text-3xl font-bold container-label">Works At</h4>

                            <div className="container-padded">
                                {/*// <!-- If a recruiter has one or more companies -->*/}
                                {(userDetails as Recruiter).companies && (userDetails as Recruiter).companies.length > 0 ? (
                                    <>
                                    <Table variant="secondary">
                                        <Table.ResizableContainer>
                                            <Table.Content aria-label="Team members" className="min-w-[600px]">
                                                <Table.Header>
                                                    <Table.Column isRowHeader>Name<Table.ColumnResizer/></Table.Column>
                                                    <Table.Column>Industry<Table.ColumnResizer/></Table.Column>
                                                    <Table.Column>Page<Table.ColumnResizer/></Table.Column>
                                                    <Table.Column>Address<Table.ColumnResizer/></Table.Column>
                                                </Table.Header>
                                                <Table.Body>
                                                    {(userDetails as Recruiter).companies.map((company: Company, index: number) => (
                                                        <Table.Row key={index}>
                                                            <Table.Cell>{company.name}</Table.Cell>
                                                            <Table.Cell>{company.industry}</Table.Cell>
                                                            <Table.Cell>{company.websiteURL.toString()}</Table.Cell>
                                                            <Table.Cell>{company.locationOfHQ}</Table.Cell>
                                                        </Table.Row>
                                                    ))}
                                                </Table.Body>
                                            </Table.Content>
                                        </Table.ResizableContainer>
                                    </Table>
                                    </>
                                ) : (
                                    <p className="auto-capitalise">
                                        — You're not working for any company.
                                    </p>
                                )}
                            </div>
                        </>
                    )}

                    {userDetails.role == "ADMIN" && (
                        <div className="flex items-center justify-center" style={{height: "50vh"}}>
                        <h1 className="text-3xl font-bold align-middle justify-center text-gray-400">We don't have anything else to show</h1>
                        </div>
                    )}

                    <br/><br/>
            </div>
        </>
    )
}