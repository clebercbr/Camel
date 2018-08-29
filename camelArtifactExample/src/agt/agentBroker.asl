scenarioBtest(false).

/* Initial goals */
!start.

+!start: scenarioBtest(L) <-
	.print("Building and linking artifacts..."); 
	makeArtifact("ArtifactC","camelartifacts.ArtifactC",[],ArtifactCid);

	if (L)
	{
		//Cenario B
 		//- ArtefatoA camel artifact
 		//- ArtefatoB artefato normal cartago
 		//- ArtefatoC camel artifact (roteador)
 		//- 10 x Artefatos do tipo B linkados com C
    	for ( .range(I,0,9999) ) {
       		.concat("ArtifactB",I,X);
       	 	makeArtifact(X,"artifacts.ArtifactB",[],Artid0);
			linkArtifacts(ArtifactCid,"out-1",Artid0);
			linkArtifacts(Artid0,"out-2",ArtifactCid);        
			!!sendKAmsg(X);
     	}
		makeArtifact("ArtifactA0","camelartifacts.ArtifactA",[],ArtifactAid);
     	!!listen("ArtifactA0");
     	!!sendKAmsg("ArtifactA0");
     	!!listen("ArtifactC");
     	!!sendKAmsg("ArtifactC")
	} else {
		//Cenario A
 		//- ArtefatoA camel artifact
 		//- ArtefatoB artefato normal cartago
 		//- ArtefatoC camel artifact (roteador)
 		//- 10 x Artefatos do tipo A (camel artifact) cada um instanciando camel e criando suas rotas
		for ( .range(I,0,9999) ) {
       		.concat("ArtifactA",I,X);
       		makeArtifact(X,"camelartifacts.ArtifactA",[],Artid0);
       		!!sendKAmsg(X);	
       		!!listen(X)
       	}
		makeArtifact("ArtifactB0","artifacts.ArtifactB",[],ArtifactBid);
		linkArtifacts(ArtifactCid,"out-1",ArtifactBid);
		linkArtifacts(ArtifactBid,"out-2",ArtifactCid);
       	!!sendKAmsg("ArtifactB0");
       	!!listen("ArtifactC");
       	!!sendKAmsg("ArtifactC")
	}	
	.print("Artifact are ready for use!").
	
+!listen(Art): true <-
	focusWhenAvailable(Art); //Just to make sure the artifact was already created
	.print("Start listening on ",Art,"..."); 
	lookupArtifact(Art,Aid);
	focus(Aid);
	listenRoutes(true)[artifact_id(Aid)]; //Start listening on artifact (blocking command)
	.print("Listening process on ",Art," finished!").

+!sendKAmsg(Art): true <- 
	focusWhenAvailable(Art); //Just to make sure the artifact was already created
	lookupArtifact(Art,Aid);
	.print("Sending keepalive message to ",Art,"!");
	focus(Aid);
	sendKA[artifact_id(Aid)];
	.wait(2000);
	!!sendKAmsg(Art).

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }
